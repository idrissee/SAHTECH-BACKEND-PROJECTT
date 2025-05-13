package com.example.Sahtech.services.Recommendation;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;

public interface RecommendationService {
    String generateRecommendation(Utilisateurs utilisateur, Produit produit);
} 