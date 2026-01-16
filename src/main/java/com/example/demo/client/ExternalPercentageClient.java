package com.example.demo.client;

import com.example.demo.exception.ExternalServiceException;
import org.springframework.stereotype.Component;

@Component
public class ExternalPercentageClient {

    public double fetchPercentage() {
        // Simulación de servicio externo
        if (Math.random() < 0.3) {
            throw new ExternalServiceException("Servicio externo caído");
        }
        return 10.0;
    }
}
