package com.example.Sahtech;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.services.Recommendation.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This test verifies that our AI service integration is working properly.
 * Note: Run this test manually as needed, not as part of the automated build.
 */
@SpringBootTest
@ActiveProfiles("test")
public class AIServiceIntegrationTest {

    @Autowired
    private RecommendationService recommendationService;

    /**
     * Test the AI service with realistic data.
     */
    @Test
    public void testAIServiceWithRealData() {
        // Create a mock user
        Utilisateurs user = new Utilisateurs();
        user.setId("test123");
        user.setNom("Test User");
        user.setSexe("male");
        user.setPoids(75.0f);
        user.setTaille(180.0f);
        
        // Set chronic diseases
        List<Maladie> maladies = new ArrayList<>();
        maladies.add(Maladie.DIABETIQUE);
        user.setMaladies(maladies);
        
        // Set allergies
        List<String> allergies = new ArrayList<>();
        allergies.add("peanuts");
        user.setAllergies(allergies);
        
        // Set objectives
        List<String> objectives = new ArrayList<>();
        objectives.add("weight loss");
        user.setObjectives(objectives);
        
        // Create a mock product
        Produit product = new Produit();
        product.setId("prod123");
        product.setNom("KOOL 4 Zinners");
        product.setCodeBarre(6133414007137L);
        product.setMarque("palmary");
        product.setCategorie("gateau");
        
        // Get recommendation
        Map<String, Object> recommendation = recommendationService.generateRecommendationWithType(user, product);
        
        // Print results
        System.out.println("=====================================================");
        System.out.println("AI Service Integration Test Results");
        System.out.println("=====================================================");
        System.out.println("Recommendation: " + recommendation.get("recommendation"));
        System.out.println("Type: " + recommendation.get("recommendation_type"));
        System.out.println("=====================================================");
    }
} 