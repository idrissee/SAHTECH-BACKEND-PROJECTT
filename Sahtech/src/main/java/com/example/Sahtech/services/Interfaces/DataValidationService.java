package com.example.Sahtech.services.Interfaces;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import java.util.Map;

/**
 * Service for data validation and conversion between different application layers
 */
public interface DataValidationService {
    
    /**
     * Validates a product entity for required fields
     * @param produit The product to validate
     * @return True if the product is valid
     */
    boolean validateProduit(Produit produit);
    
    /**
     * Converts a Flutter product map to Spring Boot Produit entity
     * @param flutterProduct The product data from Flutter frontend
     * @return A validated Produit entity
     */
    Produit fromFlutterFormat(Map<String, Object> flutterProduct);
    
    /**
     * Converts a Spring Boot Produit to FastAPI format
     * @param produit The Spring Boot product entity
     * @return A map with properly formatted data for FastAPI
     */
    Map<String, Object> toFastApiFormat(Produit produit);
    
    /**
     * Normalizes barcode values to ensure consistency
     * @param barcode The raw barcode value (could be String, Long, etc)
     * @return A normalized String barcode
     */
    String normalizeBarcode(Object barcode);
} 