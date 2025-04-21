package com.example.Sahtech.services.Impl;

import com.example.Sahtech.Dto.auth.AuthResponse;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.UtilisateursRepository;
import com.example.Sahtech.security.JwtTokenProvider;
import com.example.Sahtech.services.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {

    private final UtilisateursRepository utilisateursRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public OAuth2ServiceImpl(UtilisateursRepository utilisateursRepository, JwtTokenProvider jwtTokenProvider) {
        this.utilisateursRepository = utilisateursRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse processOAuthPostLogin(OAuth2AuthenticationToken token) {
        OAuth2User oAuth2User = token.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        
        // Check if user exists
        Optional<Utilisateurs> userOptional = utilisateursRepository.findByEmail(email);
        Utilisateurs user;
        
        if (userOptional.isEmpty()) {
            // Create new user if they don't exist
            String[] names = name.split(" ", 2);
            String firstName = names.length > 0 ? names[0] : "";
            String lastName = names.length > 1 ? names[1] : "";
            
            user = Utilisateurs.builder()
                    .email(email)
                    .prenom(firstName)
                    .nom(lastName)
                    .provider("GOOGLE")
                    .build();
            
            user = utilisateursRepository.save(user);
        } else {
            user = userOptional.get();
            // Update provider if needed
            if (!"GOOGLE".equals(user.getProvider())) {
                user.setProvider("GOOGLE");
                user = utilisateursRepository.save(user);
            }
        }
        
        // Create authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            email, 
            null, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Generate JWT token
        String jwtToken = jwtTokenProvider.generateToken(authentication, "USER", user.getId());
        
        // Return auth response
        return AuthResponse.builder()
                .token(jwtToken)
                .userType("USER")
                .userId(user.getId())
                .email(email)
                .build();
    }
} 