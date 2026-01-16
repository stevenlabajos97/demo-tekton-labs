package com.example.demo.service;

import com.example.demo.client.ExternalPercentageClient;
import com.example.demo.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PercentageService {

    private static final String CACHE_NAME = "percentage";
    private static final String CACHE_KEY = "value";

    private final ExternalPercentageClient client;
    private final CacheManager cacheManager;

    public double getPercentage() throws ExternalServiceException {
        try {
            double value = client.fetchPercentage();
            // Guardar manualmente en cache
            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null) {
                cache.put(CACHE_KEY, value);
            }
            return value;
        } catch (ExternalServiceException ex) {
            // Fallback para recuperar manualmente el valor del caché en caso de error
            Double cached = getFromCache();
            if (cached != null) return cached;
            throw ex;
        }
    }

    private Double getFromCache() {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (cache == null) return null;
        Cache.ValueWrapper wrapper = cache.get(CACHE_KEY);
        return wrapper != null ? (Double) wrapper.get() : null;
    }
}