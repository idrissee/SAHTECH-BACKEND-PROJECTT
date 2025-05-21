package com.example.Sahtech.Controllers.RecommendationControllers;

import com.example.Sahtech.Dto.Scan.HistoriqueScanDto;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Scan.HistoriqueScan;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
import com.example.Sahtech.repositories.Scan.HistoriqueScanRepository;
import com.example.Sahtech.repositories.Users.UtilisateursRepository;
import com.example.Sahtech.services.Interfaces.Scan.HistoriqueScanService;
import com.example.Sahtech.services.Recommendation.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private static final Logger logger = Logger.getLogger(RecommendationController.class.getName());

    private final RecommendationService recommendationService;
    private final UtilisateursRepository utilisateursRepository;
    private final ProduitRepository produitRepository;
    private final HistoriqueScanRepository historiqueScanRepository;
    private final HistoriqueScanService historiqueScanService;

    /**
     * Get recommendation data for a user and product
     * This endpoint is called by the Flutter app when a product is scanned
     * The method now always generates a fresh recommendation regardless of whether one already exists
     * It can also accept a Flutter callback URL to enable direct communication between FastAPI and Flutter
     */
    @GetMapping("/user/{userId}/data")
    public ResponseEntity<?> getRecommendationData(
            @PathVariable String userId, 
            @RequestParam String productId,
            @RequestParam(required = false) String flutterCallbackUrl) {
        
        logger.info("Getting recommendation for user " + userId + " and product " + productId);
        if (flutterCallbackUrl != null && !flutterCallbackUrl.isEmpty()) {
            logger.info("Flutter callback URL provided: " + flutterCallbackUrl);
        }
        
        try {
            // 1. Find user and product
            Optional<Utilisateurs> utilisateur = utilisateursRepository.findById(userId);
            Optional<Produit> produit = produitRepository.findById(productId);

            if (utilisateur.isEmpty()) {
                logger.warning("User not found: " + userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (produit.isEmpty()) {
                logger.warning("Product not found: " + productId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            logger.info("Found user: " + utilisateur.get().getNom() + " and product: " + produit.get().getNom());

            // 2. Check if a scan with recommendation already exists for this user and product
            List<HistoriqueScan> existingScans = historiqueScanRepository.findByUtilisateurIdAndProduitId(userId, productId);
            
            HistoriqueScan latestScan = null;
            if (!existingScans.isEmpty()) {
                // Find the most recent scan with a recommendation
                latestScan = existingScans.stream()
                    .filter(scan -> scan.getRecommandationIA() != null && !scan.getRecommandationIA().isEmpty())
                    .sorted((s1, s2) -> s2.getDateScan().compareTo(s1.getDateScan()))
                    .findFirst()
                    .orElse(null);
                
                if (latestScan != null) {
                    logger.info("Found existing scan with recommendation for product: " + produit.get().getNom());
                }
            }

            // 3. If a recent scan with recommendation exists, use it; otherwise generate a new recommendation
            if (latestScan != null) {
                logger.info("Using existing recommendation from scan id: " + latestScan.getId());
                
                Map<String, Object> response = new HashMap<>();
                response.put("recommendation", latestScan.getRecommandationIA());
                response.put("recommendation_type", latestScan.getRecommendationType());
                return ResponseEntity.ok(response);
                
            } else {
                try {
                    // Generate a fresh recommendation
                    logger.info("Generating fresh recommendation for product: " + produit.get().getNom());
                    Map<String, Object> aiResponse = recommendationService.generateRecommendationWithType(
                        utilisateur.get(), 
                        produit.get(),
                        flutterCallbackUrl  // Pass the Flutter callback URL to the service
                    );
                    String aiRecommendation = (String) aiResponse.get("recommendation");
                    String recommendationType = (String) aiResponse.get("recommendation_type");
                    
                    // Create a new scan with the recommendation
                    HistoriqueScan newScan = new HistoriqueScan();
                    newScan.setUtilisateur(utilisateur.get());
                    newScan.setProduit(produit.get());
                    newScan.setDateScan(LocalDateTime.now());
                    newScan.setRecommandationIA(aiRecommendation);
                    newScan.setRecommendationType(recommendationType);
                    
                    // Save the scan with recommendation
                    HistoriqueScan savedScan = historiqueScanService.saveScan(newScan);
                    logger.info("Saved new scan with recommendation, scan ID: " + savedScan.getId());
                    
                    // Return the recommendation
                    Map<String, Object> response = new HashMap<>();
                    response.put("recommendation", aiRecommendation);
                    response.put("recommendation_type", recommendationType);
                    return ResponseEntity.ok(response);
                    
                } catch (ResourceAccessException e) {
                    // Handle AI service connectivity issues
                    logger.severe("AI service connectivity error: " + e.getMessage());
                    Map<String, Object> fallbackResponse = new HashMap<>();
                    fallbackResponse.put("recommendation", "Impossible de générer une recommandation pour ce produit. Veuillez vérifier votre connexion internet ou réessayer plus tard.");
                    fallbackResponse.put("recommendation_type", "caution");
                    return ResponseEntity.ok(fallbackResponse);
                }
            }
        } catch (Exception e) {
            logger.severe("Error generating recommendation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error generating recommendation: " + e.getMessage());
        }
    }

    /**
     * Get all recommendations for a user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserRecommendations(@PathVariable String userId) {
        try {
            Optional<Utilisateurs> utilisateur = utilisateursRepository.findById(userId);
            if (utilisateur.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Find scans with recommendations for this user
            List<HistoriqueScan> scansWithRecommendations = historiqueScanRepository.findByUtilisateurId(userId).stream()
                .filter(scan -> scan.getRecommandationIA() != null && !scan.getRecommandationIA().isEmpty())
                .collect(Collectors.toList());
            
            // Convert to a simpler response format with just the recommendation data
            List<Map<String, Object>> recommendations = scansWithRecommendations.stream().map(scan -> {
                Map<String, Object> rec = new HashMap<>();
                rec.put("id", scan.getId());
                rec.put("userId", userId);
                rec.put("productId", scan.getProduit().getId());
                rec.put("productName", scan.getProduit().getNom());
                rec.put("recommendation", scan.getRecommandationIA());
                rec.put("recommendation_type", scan.getRecommendationType());
                rec.put("date", scan.getDateScan());
                return rec;
            }).collect(Collectors.toList());
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            logger.severe("Error fetching recommendations: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching recommendations: " + e.getMessage());
        }
    }
} 