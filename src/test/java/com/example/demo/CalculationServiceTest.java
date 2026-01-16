package com.example.demo;

import com.example.demo.controller.dto.CalculationResponse;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.service.CalculationService;
import com.example.demo.service.HistoryService;
import com.example.demo.service.PercentageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    @Mock
    private PercentageService percentageService;

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private CalculationService calculationService;

    @Test
    void testCalculateSuccessfully() {
        when(percentageService.getPercentage()).thenReturn(10.0);

        double num1 = 100;
        double num2 = 50;

        CalculationResponse response = calculationService.calculate(num1, num2);

        assertEquals(150.0, response.sum(), 0.001);
        assertEquals(10.0, response.percentage(), 0.001);
        assertEquals(165.0, response.result(), 0.001);

        ArgumentCaptor<String> endpointCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> paramsCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> responseCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> errorCaptor = ArgumentCaptor.forClass(String.class);

        verify(historyService, times(1))
                .saveAsync(
                        endpointCaptor.capture(),
                        paramsCaptor.capture(),
                        responseCaptor.capture(),
                        errorCaptor.capture()
                );

        Assertions.assertEquals("/api/calculate", endpointCaptor.getValue());
        Assertions.assertEquals("num1=100.0, num2=50.0", paramsCaptor.getValue());
        Assertions.assertTrue(responseCaptor.getValue().contains("sum=150.0"));
        Assertions.assertNull(errorCaptor.getValue());
    }

    @Test
    void testCalculateWhenPercentageServiceFails() {
        when(percentageService.getPercentage())
                .thenThrow(new ExternalServiceException("Servicio externo caído"));

        double num1 = 100;
        double num2 = 50;

        ExternalServiceException exception =
                Assertions.assertThrows(ExternalServiceException.class, () ->
                        calculationService.calculate(num1, num2));

        Assertions.assertEquals("Servicio externo caído", exception.getMessage());

        ArgumentCaptor<String> endpointCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> paramsCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> responseCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> errorCaptor = ArgumentCaptor.forClass(String.class);

        verify(historyService, times(1))
                .saveAsync(
                        endpointCaptor.capture(),
                        paramsCaptor.capture(),
                        responseCaptor.capture(),
                        errorCaptor.capture()
                );

        Assertions.assertEquals("/api/calculate", endpointCaptor.getValue());
        Assertions.assertEquals("num1=100.0, num2=50.0", paramsCaptor.getValue());
        Assertions.assertNull(responseCaptor.getValue());
        Assertions.assertEquals("Servicio externo caído", errorCaptor.getValue());
    }
}