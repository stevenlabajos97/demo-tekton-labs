package com.example.demo.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final long CACHE_TTL = 30;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("percentage");
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(CACHE_TTL, TimeUnit.MINUTES)
        );
        return cacheManager;
    }
}
