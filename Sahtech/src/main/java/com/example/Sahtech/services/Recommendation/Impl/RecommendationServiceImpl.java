package com.example.Sahtech.services.Recommendation.Impl;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.services.Recommendation.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    
    private static final Logger logger = Logger.getLogger(RecommendationServiceImpl.class.getName());
    
    private final RestTemplate restTemplate;
    
    @Value("${ai.service.url:http://192.168.1.69:8000}")
    private String aiServiceUrl;
    
    @Value("${ai.service.apikey:sahtech-fastapi-secure-key-2025}")
    private String aiServiceApiKey;
    
    @Override
    public String generateRecommendation(Utilisateurs utilisateur, Produit produit) {
        try {
            Map<String, Object> result = callAiService(utilisateur, produit);
            if (result != null && result.containsKey("recommendation")) {
                return (String) result.get("recommendation");
            } else {
                return "Unable to generate a recommendation at this time. Please try again later.";
            }
        } catch (Exception e) {
            logger.severe("Error calling AI service: " + e.getMessage());
            return "An error occurred while generating your recommendation: " + e.getMessage();
        }
    }
    
    @Override
    public Map<String, Object> generateRecommendationWithType(Utilisateurs utilisateur, Produit produit) {
        try {
            Map<String, Object> result = callAiService(utilisateur, produit);
            if (result == null) {
                Map<String, Object> fallback = new HashMap<>();
                fallback.put("recommendation", "Unable to generate a recommendation at this time. Please try again later.");
                fallback.put("recommendation_type", "caution");
                return fallback;
            }
            return result;
        } catch (Exception e) {
            logger.severe("Error calling AI service: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("recommendation", "An error occurred while generating your recommendation: " + e.getMessage());
            error.put("recommendation_type", "caution");
            return error;
        }
    }
    
    /**
     * Call the AI service to generate a recommendation
     */
    private Map<String, Object> callAiService(Utilisateurs utilisateur, Produit produit) {
        logger.info("Generating recommendation for user " + utilisateur.getId() + " and product " + produit.getId());
        
        try {
            // Prepare headers with API key
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-Key", aiServiceApiKey);
            
            // Prepare request body
            Map<String, Object> requestBody = new HashMap<>();
            
            // Prepare user data with field names that match your entity classes
            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", utilisateur.getId());
            userData.put("allergies", utilisateur.getAllergies() != null ? utilisateur.getAllergies() : List.of());
            userData.put("health_conditions", utilisateur.getMaladies() != null ? utilisateur.getMaladies() : List.of());
            userData.put("age", utilisateur.getAge());
            userData.put("weight", utilisateur.getPoids());
            userData.put("height", utilisateur.getTaille());
            userData.put("gender", utilisateur.getSexe());
            userData.put("activity_level", "moderate"); // Default value if not available in entity
            userData.put("objectives", utilisateur.getObjectives() != null ? utilisateur.getObjectives() : List.of());
            userData.put("has_allergies", utilisateur.getAllergies() != null && !utilisateur.getAllergies().isEmpty());
            userData.put("has_chronic_disease", utilisateur.getMaladies() != null && !utilisateur.getMaladies().isEmpty());
            
            // Prepare product data with field names that match your entity classes
            Map<String, Object> productData = new HashMap<>();
            productData.put("id", produit.getId());
            productData.put("name", produit.getNom() != null ? produit.getNom() : "Unknown Product");
            productData.put("barcode", produit.getCodeBarre());
            productData.put("brand", produit.getMarque());
            productData.put("category", produit.getCategorie());
            
            // Extract ingredient names safely with improved error handling
            List<String> ingredientNames = new ArrayList<>();
            if (produit.getIngredients() != null) {
                for (var ingredient : produit.getIngredients()) {
                    if (ingredient != null) {
                        try {
                            if (ingredient.getNomIngrediant() != null) {
                                ingredientNames.add(ingredient.getNomIngrediant().toString());
                            }
                        } catch (Exception e) {
                            logger.log(Level.WARNING, "Error processing ingredient enum: " + e.getMessage(), e);
                            // Keep processing other ingredients, don't let this one fail the whole operation
                        }
                    }
                }
            }
            productData.put("ingredients", ingredientNames);
            
            // Add additives
            productData.put("additives", produit.getNomAdditif() != null ? produit.getNomAdditif() : List.of());
            
            // Add nutri score
            String nutriScore = "C"; // Default
            if (produit.getValeurNutriScore() != null) {
                nutriScore = produit.getValeurNutriScore().toString();
            }
            productData.put("nutri_score", nutriScore);
            
            // Add nutrition values with defaults
            Map<String, Object> nutritionValues = new HashMap<>();
            nutritionValues.put("calories", 0);
            nutritionValues.put("fat", 0);
            nutritionValues.put("sugar", 0);
            nutritionValues.put("salt", 0);
            productData.put("nutrition_values", nutritionValues);
            
            // Build full request
            requestBody.put("user_data", userData);
            requestBody.put("product_data", productData);
            
            // Make API request to FastAPI
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            String url = aiServiceUrl + "/predict";
            
            logger.info("Sending request to AI service: " + url);
            logger.info("Request headers: " + headers);
            
            // Log part of the request body (omitting sensitive details)
            logger.info("Product data being sent: " + productData.get("name") + ", " + 
                        productData.get("brand") + ", " + productData.get("category"));
            
            return restTemplate.postForObject(url, request, Map.class);
            
        } catch (ResourceAccessException e) {
            // This exception occurs when there are connectivity issues
            logger.severe("Cannot connect to AI service at " + aiServiceUrl + ": " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("recommendation", "AI service unavailable. Please try again later.");
            error.put("recommendation_type", "caution");
            return error;
        } catch (RestClientException e) {
            // This occurs for other REST client issues (bad responses, etc)
            logger.severe("Error from AI service: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("recommendation", "Error receiving response from AI service. Please try again later.");
            error.put("recommendation_type", "caution");
            return error;
        } catch (Exception e) {
            // Catch any other unexpected errors
            logger.severe("Unexpected error calling AI service: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("recommendation", "An unexpected error occurred. Please try again later.");
            error.put("recommendation_type", "caution");
            return error;
        }
    }
} 