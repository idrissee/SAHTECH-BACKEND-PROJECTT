package com.example.Sahtech.services;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Service qui gère les autorisations d'accès aux ressources
 */
public interface AuthorizationService {
    
    /**
     * Vérifie si l'utilisateur courant est autorisé à accéder à une ressource utilisateur spécifique
     * @param resourceId ID de la ressource (utilisateur, nutritionniste, etc.)
     * @param request Requête HTTP pour extraire le token
     * @return true si l'utilisateur est un admin ou s'il accède à ses propres données
     */
    boolean isAuthorizedToAccessResource(String resourceId, HttpServletRequest request);
} 