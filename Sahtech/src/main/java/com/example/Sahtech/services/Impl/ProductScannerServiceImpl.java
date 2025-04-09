package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.exceptions.BarcodeRecognitionException;
import com.example.Sahtech.exceptions.ProduitNotFoundException;
import com.example.Sahtech.exceptions.UtilisateurNotFoundException;
import com.example.Sahtech.services.*;
import com.example.Sahtech.services.UtilisateursService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductScannerServiceImpl implements ProductScannerService {
    private final ProduitService produitService;
    private final HistoriqueScanService historiqueScanService;
    private final UtilisateurService utilisateursService;
    private final IAAnalysisService iaAnalysisService;

    @Override
    public HistoriqueScan scanByBarcode(String barcode, Long userId) {
        log.info("Scanning product with barcode: {} for user: {}", barcode, userId);
        
        // 1. Récupérer l'utilisateur
        Utilisateurs utilisateur = utilisateursService.findById(userId)
                .orElseThrow(() -> new UtilisateurNotFoundException(userId));
        
        // 2. Récupérer le produit par code-barres
        Produit produit = produitService.findByCodeBarre(barcode)
                .orElseThrow(() -> new ProduitNotFoundException("code-barres", barcode));
        
        // 3. Faire l'analyse avec l'IA en fonction des données de santé de l'utilisateur
        Produit produitAnalyse = analyzeProductWithAI(produit, utilisateur);
        
        // 4. Créer l'historique de scan
        HistoriqueScan historique = createHistoriqueScan(utilisateur, produitAnalyse);
        
        log.info("Scan completed successfully for barcode: {}", barcode);
        return historiqueScanService.saveScan(historique);
    }
    
    @Override
    public HistoriqueScan scanByImage(MultipartFile image, Long userId) {
        log.info("Scanning product from image for user: {}", userId);
        
        // 1. Récupérer l'utilisateur
        Utilisateurs utilisateur = utilisateursService.findById(userId)
                .orElseThrow(() -> new UtilisateurNotFoundException(userId));
        
        // 2. Identifier le produit à partir de l'image en utilisant l'IA de reconnaissance d'image
        String recognizedBarcode = iaAnalysisService.recognizeBarcodeFromImage(image);
        if (recognizedBarcode == null) {
            log.error("Failed to recognize barcode from image");
            throw new BarcodeRecognitionException();
        }
        
        log.info("Recognized barcode from image: {}", recognizedBarcode);
        
        // Utiliser le code-barres reconnu pour retrouver le produit
        return scanByBarcode(recognizedBarcode, userId);
    }
    
    /**
     * Analyse un produit pour un utilisateur spécifique en utilisant l'IA
     * @param produit Le produit à analyser
     * @param utilisateur L'utilisateur pour lequel l'analyse est faite
     * @return Le produit avec des recommandations personnalisées
     */
    private Produit analyzeProductWithAI(Produit produit, Utilisateurs utilisateur) {
        log.info("Analyzing product: {} for user: {}", produit.getId(), utilisateur.getId());
        
        // 1. Obtenir l'analyse de base du produit par l'IA
        Map<String, Object> analysisResult = iaAnalysisService.analyzeProduct(produit);
        
        // 2. Obtenir l'analyse personnalisée en fonction du profil de santé de l'utilisateur
        Map<String, Object> personalizedAnalysis = iaAnalysisService.personalizeAnalysis(analysisResult, utilisateur);
        
        // 3. Mettre à jour le produit avec les résultats de l'analyse
        produit.setPointsPositifs((List<String>) personalizedAnalysis.get("positivePoints"));
        produit.setPointsNegatifs((List<String>) personalizedAnalysis.get("negativePoints"));
        produit.setImpactSante((String) personalizedAnalysis.get("healthImpact"));
        
        // 4. Générer une recommandation personnalisée basée sur l'analyse de l'IA
        String recommandation = iaAnalysisService.generateRecommendation(produit, utilisateur);
        produit.setRecommandationIA(recommandation);
        
        log.info("Analysis completed for product: {}", produit.getId());
        return produit;
    }
    
    /**
     * Crée un historique de scan à partir d'un utilisateur et d'un produit
     * @param utilisateur L'utilisateur qui a scanné
     * @param produit Le produit scanné
     * @return L'historique de scan créé
     */
    private HistoriqueScan createHistoriqueScan(Utilisateurs utilisateur, Produit produit) {
        log.info("Creating scan history for user: {} and product: {}", utilisateur.getId(), produit.getId());
        
        String noteNutriScore = null;
        if (produit.getValeurNutriScore() != null) {
            noteNutriScore = produit.getValeurNutriScore().toString();
        } else if (produit.getNutriScore() != null) {
            // Fallback sur le champ texte si le nouveau champ n'est pas défini
            noteNutriScore = produit.getNutriScore();
        }
        
        return HistoriqueScan.builder()
                .utilisateur(utilisateur)
                .produit(produit)
                .dateScan(LocalDateTime.now())
                .noteNutriScore(noteNutriScore)
                .additifsDetectes(produit.getNomAdditif())
                .ingredients(produit.getIngredients())
                .pointsPositifs(produit.getPointsPositifs())
                .pointsNegatifs(produit.getPointsNegatifs())
                .impactSante(produit.getImpactSante())
                .recommandationIA(produit.getRecommandationIA())
                .estFavori(false)
                .build();
    }
} 