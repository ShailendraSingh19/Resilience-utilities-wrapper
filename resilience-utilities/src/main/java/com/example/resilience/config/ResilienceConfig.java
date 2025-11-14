package com.example.resilience.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    @ConditionalOnMissingBean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig defaultConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(60f)
                .slidingWindowSize(5)
                .minimumNumberOfCalls(3)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .permittedNumberOfCallsInHalfOpenState(2)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(defaultConfig);
        registry.circuitBreaker("serviceB");
        return registry;
    }

    @Bean
    @ConditionalOnMissingBean
    public RetryRegistry retryRegistry() {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();

        RetryRegistry registry = RetryRegistry.of(retryConfig);
        registry.retry("serviceBRetry");
        return registry;
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(10)) // 10 sec window
                .limitForPeriod(7) // allow 3 calls per window
                .timeoutDuration(Duration.ofMillis(0)) // do not wait
                .build();

        RateLimiterRegistry registry = RateLimiterRegistry.of(config);
        registry.rateLimiter("serviceBLimiter");
        return registry;
    }
}
