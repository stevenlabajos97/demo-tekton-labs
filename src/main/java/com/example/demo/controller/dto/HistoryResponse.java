package com.example.demo.controller.dto;

import java.time.LocalDateTime;

public record HistoryResponse(
        LocalDateTime date,
        String endpoint,
        String parameters,
        String response,
        String error
) {}

