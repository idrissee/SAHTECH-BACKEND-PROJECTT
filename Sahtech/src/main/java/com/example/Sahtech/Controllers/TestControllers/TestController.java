package com.example.Sahtech.Controllers.TestControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/API/Sahtech/test")
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
    
    @GetMapping("/test-connection")
    public Map<String, String> testConnection() {
        return Collections.singletonMap("status", "success");
    }
    
    // Root level test endpoint for easier testing
    @GetMapping(value = "/", produces = "text/plain")
    public String root() {
        return "Sahtech API is running";
    }
} 