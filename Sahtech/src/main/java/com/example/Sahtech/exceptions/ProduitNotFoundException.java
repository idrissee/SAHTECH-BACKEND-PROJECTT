package com.example.Sahtech.exceptions;

/**
 * Exception lancée lorsqu'un produit n'est pas trouvé
 */
public class ProduitNotFoundException extends RuntimeException {
    
    public ProduitNotFoundException(String message) {
        super(message);
    }
    
    public ProduitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ProduitNotFoundException(String type, String value) {
        super(String.format("Produit non trouvé avec %s: %s", type, value));
    }
} 