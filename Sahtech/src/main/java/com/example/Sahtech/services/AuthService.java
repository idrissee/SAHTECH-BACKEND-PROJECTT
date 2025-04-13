package com.example.Sahtech.services;

import com.example.Sahtech.Dto.auth.AuthResponse;
import com.example.Sahtech.Dto.auth.LoginRequest;
import com.example.Sahtech.Dto.auth.RegisterRequest;

public interface AuthService {
    /**
     * Authenticates a user and returns a JWT token
     */
    AuthResponse login(LoginRequest loginRequest);
    
    /**
     * Registers a new user and returns a JWT token
     */
    AuthResponse register(RegisterRequest registerRequest);
} 