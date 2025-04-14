package com.example.Sahtech.services.Impl;

import com.example.Sahtech.security.JwtTokenProvider;
import com.example.Sahtech.services.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Vérifie si l'utilisateur courant est autorisé à accéder à une ressource utilisateur spécifique
     * @param resourceId ID de la ressource (utilisateur, nutritionniste, etc.)
     * @param request Requête HTTP pour extraire le token
     * @return true si l'utilisateur est un admin ou s'il accède à ses propres données
     */
    @Override
    public boolean isAuthorizedToAccessResource(String resourceId, HttpServletRequest request) {
        // Récupérer l'authentification courante
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Vérifier si l'utilisateur est un admin (ils peuvent tout faire)
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true;
        }
        
        // Extraire le token JWT
        String token = extractTokenFromRequest(request);
        if (token == null) {
            return false;
        }

        // Récupérer le type d'utilisateur et son ID à partir du token
        String userType = jwtTokenProvider.getUserType(token);
        String userId = jwtTokenProvider.getUserId(token);
        
        // Vérifier si l'utilisateur accède à ses propres données
        return userId.equals(resourceId);
    }
    
    /**
     * Extrait le token JWT de la requête HTTP
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 