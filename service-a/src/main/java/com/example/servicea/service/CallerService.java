package com.example.servicea.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallerService {
    RestTemplate rest = new RestTemplate();
    @Value("${service.b.url:http://localhost:8081/api/b/data}")
    private String serviceBUrl;

    @RateLimiter(name = "serviceBLimiter", fallbackMethod = "rateLimiterFallback")
    @Retry(name = "serviceBRetry")
    @CircuitBreaker(name = "serviceB", fallbackMethod = "fallback")
    public String callB() {
        return rest.getForObject(serviceBUrl, String.class);
    }

    public String fallback(Exception ex) {
        return "{\"message\":\"fallback\",\"reason\":\"" + ex.getMessage() + "\"}";
    }

    public String rateLimiterFallback(RequestNotPermitted ex) {
        return "{\"message\":\"blocked\",\"reason\":\"rate limit exceeded\"}";
    }

}