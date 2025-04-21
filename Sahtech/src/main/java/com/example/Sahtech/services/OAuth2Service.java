package com.example.Sahtech.services;

import com.example.Sahtech.Dto.auth.AuthResponse;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface OAuth2Service {
    /**
     * Process OAuth2 authentication after successful login
     * @param token OAuth2 Authentication token
     * @return AuthResponse containing JWT token and user information
     */
    AuthResponse processOAuthPostLogin(OAuth2AuthenticationToken token);
} 