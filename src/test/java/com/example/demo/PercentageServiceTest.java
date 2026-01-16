package com.example.demo;

import com.example.demo.client.ExternalPercentageClient;
import com.example.demo.exception.ExternalServiceException;
import com.example.demo.service.PercentageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PercentageServiceTest {

    private PercentageService percentageService;
    private ExternalPercentageClient externalClient;
    private CacheManager cacheManager;

    @BeforeEach
    void setup() {
        externalClient = mock(ExternalPercentageClient.class);
        cacheManager = new ConcurrentMapCacheManager("percentage");
        percentageService = new PercentageService(externalClient, cacheManager);
    }

    @Test
    void shouldReturnPercentageWhenExternalServiceWorks() {
        // Caso éxito
        when(externalClient.fetchPercentage()).thenReturn(10.0);

        double result = percentageService.getPercentage();

        assertEquals(10.0, result);
    }

    @Test
    void shouldReturnCachedValueWhenExternalServiceFails() {
        // Llamada inicial exitosa + cachea
        when(externalClient.fetchPercentage()).thenReturn(10.0);
        double first = percentageService.getPercentage();
        assertEquals(10.0, first);

        // Servicio externo falla
        when(externalClient.fetchPercentage())
                .thenThrow(new ExternalServiceException("Servicio externo caído"));

        // Devuelve valor cacheado
        double cached = percentageService.getPercentage();
        assertEquals(10.0, cached);
    }

    @Test
    void shouldThrowExceptionWhenNoCacheAndServiceFails() {
        // Servicio externo falla y no hay cache
        when(externalClient.fetchPercentage())
                .thenThrow(new ExternalServiceException("Servicio externo caído"));

        assertThrows(ExternalServiceException.class,
                () -> percentageService.getPercentage());
    }
}