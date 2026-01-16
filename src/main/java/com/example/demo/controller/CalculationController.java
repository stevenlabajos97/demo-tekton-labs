package com.example.demo.controller;

import com.example.demo.controller.dto.CalculationResponse;
import com.example.demo.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculate")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @GetMapping
    public CalculationResponse calculate(
            @RequestParam double num1,
            @RequestParam double num2) {

        return calculationService.calculate(num1, num2);
    }
}
