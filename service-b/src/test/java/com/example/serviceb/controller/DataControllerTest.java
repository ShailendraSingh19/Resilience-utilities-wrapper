package com.example.serviceb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ServiceBController.class)
class DataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetData_Success() throws Exception {
        mockMvc.perform(get("/api/b/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("service-b"))
                .andExpect(jsonPath("$.time").exists());
    }

    @Test
    void testGetData_ContentType() throws Exception {
        mockMvc.perform(get("/api/b/data"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }
}