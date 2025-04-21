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

    /**
     * Vérifie si un nutritionniste est autorisé à accéder à une localisation.
     * @param resourceId ID de la localisation
     * @param request Requête HTTP pour extraire le token
     * @return true si l'utilisateur est un admin ou un nutritionniste accédant à sa propre localisation
     */
    boolean isNutritionisteAuthorizedForLocation(String resourceId, HttpServletRequest request);

    /**
     * Vérifie si un utilisateur a déjà scanné un produit spécifique
     * @param produitId ID du produit
     * @param request Requête HTTP pour extraire le token
     * @return true si l'utilisateur est un admin ou s'il a déjà scanné ce produit
     */
    boolean hasUserScannedProduct(String produitId, HttpServletRequest request);
}
