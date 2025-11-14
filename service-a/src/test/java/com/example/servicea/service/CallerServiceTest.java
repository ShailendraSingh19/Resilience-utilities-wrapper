package com.example.servicea.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CallerServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CallerService callerService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(callerService, "rest", restTemplate);
        ReflectionTestUtils.setField(callerService, "serviceBUrl", "http://localhost:8081/api/b/data");
    }

    @Test
    void testCallB_Success() {
        when(restTemplate.getForObject("http://localhost:8081/api/b/data", String.class))
                .thenReturn("Data from Service B");

        String result = callerService.callB();

        assertEquals("Data from Service B", result);
        verify(restTemplate).getForObject("http://localhost:8081/api/b/data", String.class);
    }

    @Test
    void testFallback() {
        RuntimeException exception = new RuntimeException("Service unavailable");

        String result = callerService.fallback(exception);

        assertEquals("{\"message\":\"fallback\",\"reason\":\"Service unavailable\"}", result);
    }

    @Test
    void testCallB_WithException() {
        when(restTemplate.getForObject("http://localhost:8081/api/b/data", String.class))
                .thenThrow(new RestClientException("Connection timeout"));

        assertThrows(RestClientException.class, () -> callerService.callB());
    }
}