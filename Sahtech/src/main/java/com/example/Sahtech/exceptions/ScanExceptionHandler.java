package com.example.Sahtech.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Gestionnaire global des exceptions pour l'application
 */
@ControllerAdvice
public class ScanExceptionHandler {
    
    /**
     * Gère les exceptions de produit non trouvé
     */
    @ExceptionHandler(ProduitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProduitNotFound(ProduitNotFoundException ex) {
        return new ResponseEntity<>(
            new ErrorResponse("PRODUIT_NOT_FOUND", ex.getMessage()), 
            HttpStatus.NOT_FOUND
        );
    }
    
    /**
     * Gère les exceptions d'utilisateur non trouvé
     */
    @ExceptionHandler(UtilisateurNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUtilisateurNotFound(UtilisateurNotFoundException ex) {
        return new ResponseEntity<>(
            new ErrorResponse("UTILISATEUR_NOT_FOUND", ex.getMessage()), 
            HttpStatus.NOT_FOUND
        );
    }
    
    /**
     * Gère les exceptions de reconnaissance de code-barres
     */
    @ExceptionHandler(BarcodeRecognitionException.class)
    public ResponseEntity<ErrorResponse> handleBarcodeRecognition(BarcodeRecognitionException ex) {
        return new ResponseEntity<>(
            new ErrorResponse("BARCODE_RECOGNITION_ERROR", ex.getMessage()), 
            HttpStatus.BAD_REQUEST
        );
    }
    
    /**
     * Gère les exceptions de taille de fichier dépassée
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeExceeded(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(
            new ErrorResponse("FILE_TOO_LARGE", "La taille du fichier dépasse la limite autorisée (10MB)"), 
            HttpStatus.PAYLOAD_TOO_LARGE
        );
    }
    
    /**
     * Gère toutes les autres exceptions non gérées
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
            new ErrorResponse("INTERNAL_SERVER_ERROR", "Une erreur interne s'est produite"), 
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
} 