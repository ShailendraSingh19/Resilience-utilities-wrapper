package com.example.serviceb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class ServiceBController {

    @GetMapping("/api/b/data")
    public ResponseEntity<String> data() {
        return ResponseEntity.ok("{\"from\":\"service-b\",\"time\":\"" + Instant.now() + "\"}") ;
    }
}
