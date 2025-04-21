package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.auth.AuthResponse;
import com.example.Sahtech.services.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/API/Sahtech/auth/login")
public class OAuth2Controller {

    @Autowired
    private OAuth2Service oAuth2Service;

    @GetMapping("/oauth2/code/google")
    public ResponseEntity<AuthResponse> oauth2LoginSuccess(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;
            AuthResponse authResponse = oAuth2Service.processOAuthPostLogin(oauthToken);
            return ResponseEntity.ok(authResponse);
        }
        return ResponseEntity.badRequest().build();
    }
} 