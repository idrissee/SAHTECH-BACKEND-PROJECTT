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
    
    @Value("${ai.service.url:http://192.168.137.15:8000}")
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
        return generateRecommendationWithType(utilisateur, produit, null);
    }
    
    @Override
    public Map<String, Object> generateRecommendationWithType(Utilisateurs utilisateur, Produit produit, String flutterCallbackUrl) {
        try {
            Map<String, Object> result = callAiService(utilisateur, produit, flutterCallbackUrl);
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
     * Overloaded method for backward compatibility
     */
    private Map<String, Object> callAiService(Utilisateurs utilisateur, Produit produit) {
        return callAiService(utilisateur, produit, null);
    }
    
    /**
     * Call the AI service to generate a recommendation
     * @param flutterCallbackUrl optional URL for FastAPI to directly send the recommendation to Flutter
     */
    private Map<String, Object> callAiService(Utilisateurs utilisateur, Produit produit, String flutterCallbackUrl) {
        logger.info("Generating recommendation for user " + utilisateur.getId() + " and product " + produit.getId());
        
        try {
            // Prepare headers with API key
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-Key", aiServiceApiKey);
            
            // Create a completely new map structure without any Java-specific metadata
            Map<String, Object> requestBody = new HashMap<>();
            
            // Prepare user data - extract only the needed fields as primitive types
            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", utilisateur.getId());
            
            // Convert allergies to string list - create a new list with defensive copying
            List<String> allergiesList = new ArrayList<>();
            if (utilisateur.getAllergies() != null) {
                for (String allergy : utilisateur.getAllergies()) {
                    if (allergy != null) {
                        allergiesList.add(allergy);
                    }
                }
            }
            userData.put("allergies", allergiesList);
            
            // Convert maladies (Enum objects) to string list for health_conditions - create a new list safely
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
                    }
                }
            }
            userData.put("health_conditions", healthConditions);
            
            // Add primitive values
            Integer age = utilisateur.getAge();
            userData.put("age", age != null ? age : null);
            
            Float poids = utilisateur.getPoids();
            userData.put("weight", poids != null ? poids.doubleValue() : null);
            
            Float taille = utilisateur.getTaille();
            userData.put("height", taille != null ? taille.doubleValue() : null);
            
            String sexe = utilisateur.getSexe();
            userData.put("gender", sexe != null ? sexe : null);
            
            userData.put("activity_level", "moderate"); // Default value
            
            // Convert objectives to string list - with defensive copying
            List<String> objectivesList = new ArrayList<>();
            if (utilisateur.getObjectives() != null && !utilisateur.getObjectives().isEmpty()) {
                for (String objective : utilisateur.getObjectives()) {
                    if (objective != null) {
                        objectivesList.add(objective);
                    }
                }
            } else if (utilisateur.getObjectif() != null) {
                objectivesList.add(utilisateur.getObjectif().name().toLowerCase().replace('_', ' '));
            }
            userData.put("objectives", objectivesList);
            
            userData.put("has_allergies", utilisateur.getAllergies() != null && !utilisateur.getAllergies().isEmpty());
            userData.put("has_chronic_disease", utilisateur.getMaladies() != null && !utilisateur.getMaladies().isEmpty());
            
            String prefLang = utilisateur.getPreferredLanguage();
            userData.put("preferred_language", prefLang != null ? prefLang : "french");
            
            // Calculate BMI if height and weight are available
            if (taille != null && poids != null && taille.compareTo(0f) > 0) {
                float heightInMeters = taille / 100.0f;
                float bmi = poids / (heightInMeters * heightInMeters);
                userData.put("bmi", Math.round(bmi * 10) / 10.0); // Round to 1 decimal place
            }
            
            // Prepare product data - extract only the needed fields as primitive types
            Map<String, Object> productData = new HashMap<>();
            productData.put("id", produit.getId());
            
            String nom = produit.getNom();
            productData.put("name", nom != null ? nom : "Unknown Product");
            
            // Ensure barcode is a string
            if (produit.getCodeBarre() != null) {
                String barcodeStr = produit.getCodeBarre().toString();
                // Remove any non-digit characters
                barcodeStr = barcodeStr.replaceAll("[^0-9]", "");
                productData.put("barcode", barcodeStr);
            } else {
                productData.put("barcode", "0000000000000");
            }
            
            String marque = produit.getMarque();
            productData.put("brand", marque != null ? marque : "Unknown Brand");
            
            String categorie = produit.getCategorie();
            productData.put("category", categorie != null ? categorie : "Unknown Category");
            
            String description = produit.getDescription();
            productData.put("description", description != null ? description : "");
            
            productData.put("type", categorie != null ? categorie : "Unknown Type");
            
            // Extract ingredient names safely with improved error handling
            List<String> ingredientNames = new ArrayList<>();
            if (produit.getIngredients() != null) {
                for (var ingredient : produit.getIngredients()) {
                    if (ingredient != null && ingredient.getNomIngrediant() != null) {
                        try {
                            ingredientNames.add(ingredient.getNomIngrediant().toString());
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
            
            // Add additives - create a new list with defensive copying
            List<String> additives = new ArrayList<>();
            if (produit.getNomAdditif() != null) {
                for (String additive : produit.getNomAdditif()) {
                    if (additive != null) {
                        additives.add(additive);
                    }
                }
            }
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
            
            // Add Flutter callback URL if provided
            if (flutterCallbackUrl != null && !flutterCallbackUrl.isEmpty()) {
                logger.info("Including Flutter callback URL in request to FastAPI: " + flutterCallbackUrl);
                requestBody.put("flutter_callback_url", flutterCallbackUrl);
            }
            
            // Log for debugging (without entire content)
            logger.info("Sending request to FastAPI with data: userData(" + userData.size() + " fields), productData(" + productData.size() + " fields)");
            
            // Make API request to FastAPI
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            String url = aiServiceUrl + "/predict";
            
            logger.info("Sending request to AI service: " + url);
            
            // Get the response as a simple Map to avoid serialization issues
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            
            // Explicitly create a new map with string values to avoid serialization problems
            Map<String, Object> sanitizedResponse = new HashMap<>();
            if (response != null) {
                // Extract the recommendation text and ensure it's a String
                if (response.containsKey("recommendation")) {
                    sanitizedResponse.put("recommendation", String.valueOf(response.get("recommendation")));
                }
                
                // Extract the recommendation_type and ensure it's a String
                if (response.containsKey("recommendation_type")) {
                    sanitizedResponse.put("recommendation_type", String.valueOf(response.get("recommendation_type")));
                }
            } else {
                // Handle null response
                sanitizedResponse.put("recommendation", "Unable to generate recommendation. Service returned null.");
                sanitizedResponse.put("recommendation_type", "caution");
            }
            
            logger.info("Received and sanitized AI service response: " + sanitizedResponse);
            return sanitizedResponse;
            
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