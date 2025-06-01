package com.example.Sahtech.exceptions;

/**
 * Exception lancée lorsqu'un utilisateur n'est pas trouvé
 */
public class UtilisateurNotFoundException extends RuntimeException {
    
    public UtilisateurNotFoundException(String message) {
        super(message);
    }
    
    public UtilisateurNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UtilisateurNotFoundException(Long userId) {
        super(String.format("Utilisateur non trouvé avec ID: %d", userId));
    }
} 