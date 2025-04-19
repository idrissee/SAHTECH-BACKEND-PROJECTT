package com.example.Sahtech.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API/Sahtech/test")
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
} 