package com.example.demo.controller;

import com.example.demo.controller.dto.HistoryResponse;
import com.example.demo.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService service;

    @GetMapping
    public List<HistoryResponse> getHistory(Pageable pageable) {
        return service.getHistory(pageable);
    }
}

