package com.example.demo.service;

import com.example.demo.controller.dto.HistoryResponse;
import com.example.demo.entity.CallHistory;
import com.example.demo.repository.CallHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final CallHistoryRepository repository;

    @Async
    public void saveAsync(String endpoint,
                          String params,
                          String response,
                          String error) {

        CallHistory history = new CallHistory();
        history.setDate(LocalDateTime.now());
        history.setEndpoint(endpoint);
        history.setParameters(params);
        history.setResponse(response);
        history.setError(error);

        repository.save(history);
    }

    public List<HistoryResponse> getHistory(Pageable pageable) {
        Page<CallHistory> callHistory = repository.findAll(pageable);
        // Aqui podriamos utilizar tambien algun mapper como MapStruct
        return callHistory
                .getContent()
                .stream()
                .map(historyEntry ->
                        new HistoryResponse(
                                historyEntry.getDate(),
                                historyEntry.getEndpoint(),
                                historyEntry.getParameters(),
                                historyEntry.getResponse(),
                                historyEntry.getError()))
                .toList();
    }
}

