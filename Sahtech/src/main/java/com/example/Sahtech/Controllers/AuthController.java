package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.auth.AuthResponse;
import com.example.Sahtech.Dto.auth.LoginRequest;
import com.example.Sahtech.Dto.auth.LogoutRequest;
import com.example.Sahtech.Dto.auth.LogoutResponse;
import com.example.Sahtech.Dto.auth.RegisterRequest;
import com.example.Sahtech.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/API/Sahtech/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        return ResponseEntity.ok(authService.logout(logoutRequest));
    }
} 