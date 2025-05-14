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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/API/Sahtech/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

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
        try {
            // 1. Find user and product
            Optional<Utilisateurs> utilisateur = utilisateursRepository.findById(userId);
            Optional<Produit> produit = produitRepository.findById(productId);

            if (utilisateur.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (produit.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            // 2. Check if a recommendation already exists
            Optional<Recommendation> existingRecommendation = 
                recommendationRepository.findByUtilisateurAndProduit(utilisateur.get(), produit.get());

            if (existingRecommendation.isPresent()) {
                // Return the existing recommendation
                Map<String, Object> response = new HashMap<>();
                response.put("recommendation", existingRecommendation.get().getContent());
                response.put("recommendation_type", existingRecommendation.get().getType());
                return ResponseEntity.ok(response);
            }

            // 3. Generate a new recommendation using the FastAPI service
            Map<String, Object> aiResponse = recommendationService.generateRecommendationWithType(utilisateur.get(), produit.get());
            String aiRecommendation = (String) aiResponse.get("recommendation");
            String recommendationType = (String) aiResponse.get("recommendation_type");

            // 4. Save the recommendation to the database with its type
            Recommendation newRecommendation = new Recommendation(utilisateur.get(), produit.get(), aiRecommendation, recommendationType);
            recommendationRepository.save(newRecommendation);

            // 5. Return the recommendation and its type
            Map<String, Object> response = new HashMap<>();
            response.put("recommendation", aiRecommendation);
            response.put("recommendation_type", recommendationType);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error saving recommendation: " + e.getMessage());
        }
    }
} 