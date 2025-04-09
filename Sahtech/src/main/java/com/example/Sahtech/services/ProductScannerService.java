package com.example.Sahtech.services;

import com.example.Sahtech.entities.HistoriqueScan;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service responsable du scan des produits
 */
public interface ProductScannerService {
    
    /**
     * Scanne un produit par code-barres et crée un historique de scan
     * @param barcode Le code-barres du produit
     * @param userId L'identifiant de l'utilisateur qui scanne
     * @return L'historique de scan créé
     */
    HistoriqueScan scanByBarcode(String barcode, Long userId);
    
    /**
     * Scanne un produit par image et crée un historique de scan
     * @param image L'image du produit
     * @param userId L'identifiant de l'utilisateur qui scanne
     * @return L'historique de scan créé
     */
    HistoriqueScan scanByImage(MultipartFile image, Long userId);
} 