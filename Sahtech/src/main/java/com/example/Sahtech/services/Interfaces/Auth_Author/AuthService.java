package com.example.Sahtech.services.Interfaces.Auth_Author;

import com.example.Sahtech.Dto.auth.AuthResponse;
import com.example.Sahtech.Dto.auth.LoginRequest;
import com.example.Sahtech.Dto.auth.LogoutRequest;
import com.example.Sahtech.Dto.auth.LogoutResponse;
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
    
    /**
     * Logs out a user by invalidating their JWT token
     */
    LogoutResponse logout(LogoutRequest logoutRequest);
}
