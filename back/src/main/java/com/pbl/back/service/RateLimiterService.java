package com.pbl.back.service;

import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterService {

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final int requestsPerMinute;

    public RateLimiterService(@Value("${rate-limit.per-minute:60}") int requestsPerMinute) {
        if (requestsPerMinute < 1) {
            throw new IllegalArgumentException("rate-limit.per-minute must be greater than zero");
        }
        this.requestsPerMinute = requestsPerMinute;
    }

    public boolean tryConsume(String key) {
        Bucket bucket = buckets.computeIfAbsent(key, ignored -> Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(requestsPerMinute)
                        .refillGreedy(requestsPerMinute, Duration.ofMinutes(1)))
                .build());
        return bucket.tryConsume(1);
    }
}
