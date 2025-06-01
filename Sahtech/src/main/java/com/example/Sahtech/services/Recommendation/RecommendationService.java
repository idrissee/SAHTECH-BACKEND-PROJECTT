package com.example.Sahtech.services.Recommendation;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;

import java.util.Map;

public interface RecommendationService {
    /**
     * Generate a basic recommendation
     * @param utilisateur the user
     * @param produit the product
     * @return recommendation text
     */
    String generateRecommendation(Utilisateurs utilisateur, Produit produit);
    
    /**
     * Generate a recommendation with type information
     * @param utilisateur the user
     * @param produit the product
     * @return a map containing the recommendation text and type
     */
    Map<String, Object> generateRecommendationWithType(Utilisateurs utilisateur, Produit produit);
    
    /**
     * Generate a recommendation with type information and an optional Flutter callback URL
     * @param utilisateur the user
     * @param produit the product
     * @param flutterCallbackUrl optional URL for FastAPI to directly send the recommendation to Flutter
     * @return a map containing the recommendation text and type
     */
    Map<String, Object> generateRecommendationWithType(Utilisateurs utilisateur, Produit produit, String flutterCallbackUrl);
} 