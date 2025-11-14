package com.example.Sahtech.exceptions;

/**
 * Exception lancée lorsque la reconnaissance d'un code-barres échoue
 */
public class BarcodeRecognitionException extends RuntimeException {
    
    public BarcodeRecognitionException(String message) {
        super(message);
    }
    
    public BarcodeRecognitionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BarcodeRecognitionException() {
        super("Impossible de reconnaître le code-barres dans l'image fournie");
    }
} 