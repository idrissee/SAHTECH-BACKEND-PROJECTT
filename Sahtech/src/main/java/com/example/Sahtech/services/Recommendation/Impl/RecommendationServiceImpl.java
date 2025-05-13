package com.example.Sahtech.services.Recommendation.Impl;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.services.Recommendation.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    
    @Override
    public String generateRecommendation(Utilisateurs utilisateur, Produit produit) {
        // Placeholder implementation - in a real scenario, you would call the AI service here
        return "Based on your profile and this product's information, we recommend moderate consumption.";
    }
} 