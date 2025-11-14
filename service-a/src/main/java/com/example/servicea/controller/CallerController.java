package com.example.servicea.controller;

import com.example.servicea.service.CallerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallerController {

    private final CallerService callerService;

    public CallerController(CallerService callerService) {
        this.callerService = callerService;
    }

    @GetMapping("/api/a/call-b")
    public ResponseEntity<String> callB() {
        return ResponseEntity.ok(callerService.callB());
    }
}
