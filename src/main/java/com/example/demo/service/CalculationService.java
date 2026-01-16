package com.example.demo.service;

import com.example.demo.controller.dto.CalculationResponse;
import com.example.demo.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final PercentageService percentageService;
    private final HistoryService historyService;

    public CalculationResponse calculate(double num1, double num2) {
        double sum = num1 + num2;
        double percentage = 0;

        try {
            // Intentamos obtener el porcentaje del servicio externo
            percentage = percentageService.getPercentage();
        } catch (ExternalServiceException ex) {
            // Solo entramos aquí si no hay cache
            historyService.saveAsync(
                    "/api/calculate",
                    "num1=" + num1 + ", num2=" + num2,
                    null,
                    ex.getMessage()
            );
            throw ex; // Se propaga para el @ExceptionHandler
        }

        // Calculamos el resultado usando el porcentaje obtenido (o cache si hubo)
        double result = sum + (sum * percentage / 100);

        CalculationResponse response = new CalculationResponse(sum, percentage, result);

        // Guardamos el histórico
        historyService.saveAsync(
                "/api/calculate",
                "num1=" + num1 + ", num2=" + num2,
                response.toString(),
                null
        );

        return response;
    }
}