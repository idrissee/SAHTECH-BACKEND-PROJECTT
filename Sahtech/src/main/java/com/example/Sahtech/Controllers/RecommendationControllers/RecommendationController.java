package com.example.Sahtech.Controllers.RecommendationControllers;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Recommendation.Recommendation;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
import com.example.Sahtech.repositories.Recommendation.RecommendationRepository;
import com.example.Sahtech.repositories.Users.UtilisateursRepository;
import com.example.Sahtech.services.Recommendation.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/API/Sahtech/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private static final Logger logger = Logger.getLogger(RecommendationController.class.getName());

    private final RecommendationService recommendationService;
    private final RecommendationRepository recommendationRepository;
    private final UtilisateursRepository utilisateursRepository;
    private final ProduitRepository produitRepository;

    /**
     * Get recommendation data for a user and product
     * This endpoint is called by the Flutter app when a product is scanned
     */
    @GetMapping("/user/{userId}/data")
    public ResponseEntity<?> getRecommendationData(@PathVariable String userId, @RequestParam String productId) {
        logger.info("Getting recommendation for user " + userId + " and product " + productId);
        
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

            // 2. Check if a recommendation already exists
            Optional<Recommendation> existingRecommendation = 
                recommendationRepository.findByUtilisateurAndProduit(utilisateur.get(), produit.get());

            if (existingRecommendation.isPresent()) {
                // Return the existing recommendation
                logger.info("Found existing recommendation for product: " + produit.get().getNom());
                Map<String, Object> response = new HashMap<>();
                response.put("recommendation", existingRecommendation.get().getContent());
                response.put("recommendation_type", existingRecommendation.get().getType());
                return ResponseEntity.ok(response);
            }

            // 3. Generate a new recommendation using the FastAPI service
            try {
                logger.info("Generating new recommendation for product: " + produit.get().getNom());
                Map<String, Object> aiResponse = recommendationService.generateRecommendationWithType(utilisateur.get(), produit.get());
                String aiRecommendation = (String) aiResponse.get("recommendation");
                String recommendationType = (String) aiResponse.get("recommendation_type");

                // 4. Save the recommendation to the database with its type
                Recommendation newRecommendation = new Recommendation(utilisateur.get(), produit.get(), aiRecommendation, recommendationType);
                recommendationRepository.save(newRecommendation);
                logger.info("Saved new recommendation for product: " + produit.get().getNom());

                // 5. Return the recommendation and its type
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

            List<Recommendation> recommendations = recommendationRepository.findByUtilisateur(utilisateur.get());
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            logger.severe("Error fetching recommendations: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching recommendations: " + e.getMessage());
        }
    }

    /**
     * Save a recommendation
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveRecommendation(@RequestBody Map<String, String> request) {
        try {
            String userId = request.get("userId");
            String productId = request.get("productId");
            String content = request.get("recommendation");
            String recommendationType = request.get("recommendationType");
            
            if (recommendationType == null || recommendationType.isEmpty()) {
                recommendationType = "caution"; // Default value
            }

            Optional<Utilisateurs> utilisateur = utilisateursRepository.findById(userId);
            Optional<Produit> produit = produitRepository.findById(productId);

            if (utilisateur.isEmpty() || produit.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }

            Recommendation recommendation = new Recommendation(utilisateur.get(), produit.get(), content, recommendationType);
            recommendationRepository.save(recommendation);

            return ResponseEntity.status(HttpStatus.CREATED).body(recommendation);
        } catch (Exception e) {
            logger.severe("Error saving recommendation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error saving recommendation: " + e.getMessage());
        }
    }
} 