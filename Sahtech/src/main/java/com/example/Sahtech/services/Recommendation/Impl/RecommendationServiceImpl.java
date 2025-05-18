package com.example.Sahtech.services.Recommendation.Impl;

import com.example.Sahtech.Enum.Maladie;
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
            
            // Prepare user data with field names that match the FastAPI UserData model
            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", utilisateur.getId());
            
            // Convert allergies to string list
            List<String> allergiesList = utilisateur.getAllergies() != null ? utilisateur.getAllergies() : List.of();
            userData.put("allergies", allergiesList);
            
            // Convert maladies (Enum objects) to string list for health_conditions
            List<String> healthConditions = new ArrayList<>();
            if (utilisateur.getMaladies() != null) {
                for (Maladie maladie : utilisateur.getMaladies()) {
                    if (maladie != null) {
                        // Convert enum name to a more user-friendly format that matches Flutter selections
                        String maladieStr;
                        switch(maladie) {
                            case DIABETIQUE:
                                maladieStr = "diabète";
                                break;
                            case HYPERTENSION:
                                maladieStr = "hypertension artérielle";
                                break;
                            case OBESITE:
                                maladieStr = "obésité";
                                break;
                            case CHOLESTEROL:
                                maladieStr = "cholestérol élevé";
                                break;
                            case MALADIE_CARDIAQUE:
                                maladieStr = "maladie cardiaque";
                                break;
                            case INTOLERANCE_LACTOSE:
                                maladieStr = "intolérance au lactose";
                                break;
                            case CELIAQUIE:
                            case MALADIE_COELIAQUE:
                                maladieStr = "maladie coeliaque";
                                break;
                            case ALLERGIE_ARACHIDES:
                                maladieStr = "allergie aux arachides";
                                break;
                            case ALLERGIE_GLUTEN:
                                maladieStr = "allergie au gluten";
                                break;
                            case ALLERGIE_FRUITS_DE_MER:
                                maladieStr = "allergie aux fruits de mer";
                                break;
                            case MALADIE_RENALE:
                                maladieStr = "maladie rénale";
                                break;
                            case HEPATITE:
                                maladieStr = "hépatite";
                                break;
                            case ASTHME:
                                maladieStr = "asthme";
                                break;
                            default:
                                // Fallback to standard enum name conversion
                                maladieStr = maladie.name().toLowerCase().replace('_', ' ');
                                break;
                        }
                        healthConditions.add(maladieStr);
                        logger.info("Added health condition: " + maladieStr + " from enum " + maladie.name());
                    }
                }
            }
            userData.put("health_conditions", healthConditions);
            
            userData.put("age", utilisateur.getAge()); // Age is already a primitive int
            userData.put("weight", utilisateur.getPoids() != null ? utilisateur.getPoids() : 70.0); // Default weight if null
            userData.put("height", utilisateur.getTaille() != null ? utilisateur.getTaille() : 170.0); // Default height if null
            userData.put("gender", utilisateur.getSexe() != null ? utilisateur.getSexe() : "not_specified");
            userData.put("activity_level", "moderate"); // Default value
            
            // Convert objectives to string list
            List<String> objectivesList = new ArrayList<>();
            if (utilisateur.getObjectives() != null && !utilisateur.getObjectives().isEmpty()) {
                // User-defined objectives have priority
                objectivesList = utilisateur.getObjectives();
            } else if (utilisateur.getObjectif() != null) {
                // Add main objective if defined
                objectivesList.add(utilisateur.getObjectif().name().toLowerCase().replace('_', ' '));
            }
            userData.put("objectives", objectivesList);
            
            userData.put("has_allergies", utilisateur.getAllergies() != null && !utilisateur.getAllergies().isEmpty());
            userData.put("has_chronic_disease", utilisateur.getMaladies() != null && !utilisateur.getMaladies().isEmpty());
            userData.put("preferred_language", utilisateur.getPreferredLanguage() != null ? utilisateur.getPreferredLanguage() : "french"); // Default language
            
            // Calculate BMI if height and weight are available
            if (utilisateur.getTaille() != null && utilisateur.getPoids() != null && utilisateur.getTaille().compareTo(0f) > 0) {
                float heightInMeters = utilisateur.getTaille() / 100.0f;
                float bmi = utilisateur.getPoids() / (heightInMeters * heightInMeters);
                userData.put("bmi", Math.round(bmi * 10) / 10.0); // Round to 1 decimal place
            }
            
            // Prepare product data with field names that match the FastAPI ProductData model
            Map<String, Object> productData = new HashMap<>();
            productData.put("id", produit.getId());
            productData.put("name", produit.getNom() != null ? produit.getNom() : "Unknown Product");
            
            // Ensure barcode is a string
            if (produit.getCodeBarre() != null) {
                String barcodeStr = produit.getCodeBarre().toString();
                // Remove any non-digit characters
                barcodeStr = barcodeStr.replaceAll("[^0-9]", "");
                productData.put("barcode", barcodeStr);
            } else {
                productData.put("barcode", "0000000000000");
            }
            
            productData.put("brand", produit.getMarque() != null ? produit.getMarque() : "Unknown Brand");
            productData.put("category", produit.getCategorie() != null ? produit.getCategorie() : "Unknown Category");
            productData.put("description", ""); // Empty description is fine
            productData.put("type", produit.getCategorie() != null ? produit.getCategorie() : "Unknown Type");
            
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
                        }
                    }
                }
            }
            if (ingredientNames.isEmpty()) {
                ingredientNames.add("No ingredients information available");
            }
            productData.put("ingredients", ingredientNames);
            
            // Add additives (ensure it's never null)
            List<String> additives = produit.getNomAdditif() != null ? produit.getNomAdditif() : new ArrayList<>();
            productData.put("additives", additives);
            
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
            nutritionValues.put("carbs", 0);
            nutritionValues.put("protein", 0);
            nutritionValues.put("salt", 0);
            productData.put("nutrition_values", nutritionValues);
            
            // Build full request - make sure the keys match exactly what FastAPI expects
            requestBody.put("user_data", userData);
            requestBody.put("product_data", productData);
            
            // Log the full request for debugging
            logger.info("Full request data structure: " + requestBody.keySet());
            logger.info("User data fields: " + userData.keySet());
            logger.info("Product data fields: " + productData.keySet());
            
            // Make API request to FastAPI
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            String url = aiServiceUrl + "/predict";
            
            logger.info("Sending request to AI service: " + url);
            
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
            
            // If this is a 422 error, log more diagnostic information
            if (e.getMessage() != null && e.getMessage().contains("422")) {
                logger.severe("422 Unprocessable Entity error - likely a data validation issue");
                logger.severe("Check that all fields match the FastAPI model requirements");
                logger.severe("Common issues: null values, wrong data types, or missing required fields");
            }
            
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