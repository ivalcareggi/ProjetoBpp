package com.project.fgh.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/") // healthcheck de lei, kubernetes? 😳 
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = Map.of(
            "status", "UP",
            "timestamp", Instant.now().toString()
        );
        return ResponseEntity.ok(response);
    }
}