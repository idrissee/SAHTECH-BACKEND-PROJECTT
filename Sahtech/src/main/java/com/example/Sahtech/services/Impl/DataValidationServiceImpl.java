package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.services.Interfaces.DataValidationService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the DataValidationService interface
 * Handles data conversion and validation between different application layers
 */
@Service
public class DataValidationServiceImpl implements DataValidationService {

    /**
     * Validates a product entity for required fields
     * @param produit The product to validate
     * @return True if the product is valid
     */
    @Override
    public boolean validateProduit(Produit produit) {
        // Check for null product
        if (produit == null) {
            return false;
        }
        
        // Check required fields
        if (produit.getCodeBarre() == null) {
            return false;
        }
        
        if (produit.getNom() == null || produit.getNom().isEmpty()) {
            return false;
        }
        
        // All validations passed
        return true;
    }
    
    /**
     * Converts a Flutter product map to Spring Boot Produit entity
     * @param flutterProduct The product data from Flutter frontend
     * @return A validated Produit entity
     */
    @Override
    public Produit fromFlutterFormat(Map<String, Object> flutterProduct) {
        if (flutterProduct == null) {
            return null;
        }
        
        // Create new product entity
        Produit produit = new Produit();
        
        // Set ID if available
        if (flutterProduct.containsKey("id")) {
            produit.setId(String.valueOf(flutterProduct.get("id")));
        }
        
        // Handle barcode field (standardize to String)
        Object rawBarcode = flutterProduct.get("barcode");
        if (rawBarcode == null) {
            // Try fallback names
            rawBarcode = flutterProduct.get("codeBarre");
        }
        
        // Normalize barcode value
        produit.setCodeBarre(normalizeBarcode(rawBarcode));
        
        // Map fields with different naming conventions
        produit.setNom(getStringValue(flutterProduct, "name", "nom"));
        produit.setMarque(getStringValue(flutterProduct, "brand", "marque"));
        produit.setCategorie(getStringValue(flutterProduct, "category", "categorie"));
        produit.setDescription(getStringValue(flutterProduct, "description", ""));
        produit.setImageUrl(getStringValue(flutterProduct, "imageUrl", "image"));
        
        // Process ingredients as list if available
        if (flutterProduct.containsKey("ingredients") && flutterProduct.get("ingredients") instanceof List) {
            List<?> rawIngredients = (List<?>) flutterProduct.get("ingredients");
            // Handle ingredient conversion in a separate method if needed
        }
        
        return produit;
    }
    
    /**
     * Converts a Spring Boot Produit to FastAPI format
     * @param produit The Spring Boot product entity
     * @return A map with properly formatted data for FastAPI
     */
    @Override
    public Map<String, Object> toFastApiFormat(Produit produit) {
        if (produit == null) {
            return null;
        }
        
        // Create map for FastAPI format
        Map<String, Object> fastApiFormat = new HashMap<>();
        
        // Standard fields
        fastApiFormat.put("id", produit.getId());
        fastApiFormat.put("name", produit.getNom());
        fastApiFormat.put("barcode", produit.getCodeBarre());  // Use barcode for FastAPI
        fastApiFormat.put("brand", produit.getMarque());
        fastApiFormat.put("category", produit.getCategorie());
        fastApiFormat.put("description", produit.getDescription());
        fastApiFormat.put("type", produit.getCategorie());
        
        // Convert ingredients to string list for FastAPI - with improved error handling
        List<String> ingredientStrings = new ArrayList<>();
        if (produit.getIngredients() != null) {
            produit.getIngredients().forEach(ingredient -> {
                if (ingredient != null) {
                    try {
                        if (ingredient.getNomIngrediant() != null) {
                            ingredientStrings.add(ingredient.getNomIngrediant().toString());
                        }
                    } catch (Exception e) {
                        // Log error but don't crash
                        System.err.println("Error processing ingredient: " + e.getMessage());
                        // Try to include the ingredient name for debugging
                        try {
                            ingredientStrings.add("UNKNOWN_INGREDIENT");
                        } catch (Exception ignored) {
                            // Completely ignore any further errors
                        }
                    }
                }
            });
        }
        fastApiFormat.put("ingredients", ingredientStrings);
        
        // Add additives from product
        fastApiFormat.put("additives", produit.getNomAdditif() != null ? produit.getNomAdditif() : new ArrayList<>());
        
        // Nutrient info
        fastApiFormat.put("nutri_score", produit.getValeurNutriScore() != null ? 
                                       produit.getValeurNutriScore().toString() : null);
        fastApiFormat.put("nutri_score_description", produit.getDescriptionNutriScore());
        
        // Create empty nutrition values map if needed
        fastApiFormat.put("nutrition_values", new HashMap<String, Object>());
        
        return fastApiFormat;
    }
    
    /**
     * Normalizes barcode values to ensure consistency
     * @param barcode The raw barcode value (could be String, Long, etc)
     * @return A normalized Long barcode
     */
    @Override
    public Long normalizeBarcode(Object barcode) {
        if (barcode == null) {
            return null;
        }
        
        try {
            // If already a Long, return it
            if (barcode instanceof Long) {
                return (Long) barcode;
            }
            
            // Convert to string
            String barcodeStr = String.valueOf(barcode);
            
            // Remove any non-digit characters
            barcodeStr = barcodeStr.replaceAll("[^0-9]", "");
            
            if (barcodeStr.isEmpty()) {
                return null;
            }
            
            // Parse to Long
            return Long.parseLong(barcodeStr);
        } catch (NumberFormatException e) {
            System.err.println("Failed to normalize barcode: " + barcode);
            return null;
        }
    }
    
    /**
     * Helper method to get string value from map with multiple possible key names
     */
    private String getStringValue(Map<String, Object> map, String... possibleKeys) {
        for (String key : possibleKeys) {
            if (map.containsKey(key) && map.get(key) != null) {
                return String.valueOf(map.get(key));
            }
        }
        return "";
    }
} 