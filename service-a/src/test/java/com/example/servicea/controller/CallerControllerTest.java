package com.example.servicea.controller;

import com.example.servicea.service.CallerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CallerController.class)
class CallerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CallerService callerService;

    @Test
    void testCallB_Success() throws Exception {
        when(callerService.callB()).thenReturn("Success response from Service B");

        mockMvc.perform(get("/api/a/call-b"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success response from Service B"));
    }

    @Test
    void testCallB_Fallback() throws Exception {
        when(callerService.callB()).thenReturn("{\"message\":\"fallback\",\"reason\":\"Connection refused\"}");

        mockMvc.perform(get("/api/a/call-b"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"fallback\",\"reason\":\"Connection refused\"}"));
    }
}