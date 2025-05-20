package com.example.Sahtech.Controllers.ScanControllers;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.ProduitService;
import com.example.Sahtech.services.Interfaces.DataValidationService;
import com.example.Sahtech.services.Interfaces.Users.UtilisateursService;
import com.example.Sahtech.services.Recommendation.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/API/Sahtech/scan")
@RequiredArgsConstructor
public class ProductScanController {

    private final ProduitService produitService;
    private final ProduitRepository produitRepository;
    private final DataValidationService dataValidationService;
    private final UtilisateursService utilisateursService;
    private final RecommendationService recommendationService;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Endpoint for scanning product by barcode
     */
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<?> scanProductByBarcode(
            @PathVariable String barcode,
            @RequestParam(required = false) String userId) {
        try {
            System.out.println("===== PRODUCT SCAN CONTROLLER DEBUG =====");
            System.out.println("Received barcode: " + barcode + " (Type: " + barcode.getClass().getSimpleName() + ")");
            System.out.println("User ID (if provided): " + userId);
            
            // Normalize barcode to ensure consistency
            Long normalizedBarcode = dataValidationService.normalizeBarcode(barcode);
            System.out.println("Normalized barcode: " + normalizedBarcode);
            
            if (normalizedBarcode == null) {
                Map<String, String> response = new HashMap<>();
                response.put("status", "invalid_barcode");
                response.put("message", "Invalid barcode format: " + barcode);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Special handling for known problematic barcode
            Optional<Produit> produit;
            if (normalizedBarcode.equals(6133414007137L)) {
                System.out.println("Using exact query method for priority barcode");
                // First try the exact query method
                produit = produitRepository.findByCodeBarreExact(normalizedBarcode);
                // If that fails, fall back to standard method
                if (!produit.isPresent()) {
                    System.out.println("Exact query failed, falling back to standard method");
                    produit = produitService.findByCodeBarre(normalizedBarcode);
                }
            } else {
                // Standard lookup for other barcodes
                produit = produitService.findByCodeBarre(normalizedBarcode);
            }
            
            System.out.println("Product found: " + produit.isPresent());
            
            // If not found, return not found response
            if (produit.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("status", "not_found");
                response.put("message", "Product not found with barcode: " + normalizedBarcode);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            System.out.println("Returning product with ID: " + produit.get().getId());
            
            // If userId is provided, asynchronously send data to AI for immediate processing
            if (userId != null && !userId.isEmpty()) {
                final Produit foundProduct = produit.get(); // Create final copy for lambda
                CompletableFuture.runAsync(() -> {
                    try {
                        System.out.println("Sending product and user data to AI service...");
                        sendProductAndUserDataToAI(foundProduct, userId);
                    } catch (Exception e) {
                        System.out.println("Error sending data to AI: " + e.getMessage());
                    }
                });
            }
            
            System.out.println("===== END PRODUCT SCAN CONTROLLER DEBUG =====");
            return ResponseEntity.ok(produit.get());
        } catch (Exception e) {
            System.out.println("Error in scanProductByBarcode: " + e.getMessage());
            e.printStackTrace();  // Print full stack trace for better debugging
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Error scanning product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Send product and user data to AI service
     */
    private void sendProductAndUserDataToAI(Produit produit, String userId) {
        try {
            // Try to get user data if available
            Utilisateurs user = utilisateursService.getUtilisateurById(userId);
            
            // Create payload for AI service
            Map<String, Object> aiPayload = new HashMap<>();
            
            try {
                aiPayload.put("product", dataValidationService.toFastApiFormat(produit));
            } catch (Exception e) {
                System.out.println("Error formatting product data: " + e.getMessage());
                // Create minimal product data to continue
                Map<String, Object> minimalProduct = new HashMap<>();
                minimalProduct.put("id", produit.getId());
                minimalProduct.put("name", produit.getNom() != null ? produit.getNom() : "Unknown Product");
                minimalProduct.put("barcode", produit.getCodeBarre());
                aiPayload.put("product", minimalProduct);
            }
            
            // Add user data if available
            if (user != null) {
                try {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("id", user.getId());
                    userData.put("nom", user.getNom());
                    userData.put("prenom", user.getPrenom());
                    userData.put("allergies", user.getAllergies());
                    userData.put("preferences", user.getObjectives());
                    userData.put("healthConditions", user.getMaladies());
                    aiPayload.put("user", userData);
                } catch (Exception e) {
                    System.out.println("Error formatting user data: " + e.getMessage());
                    // Add minimal user data
                    aiPayload.put("user", Map.of("id", userId));
                }
            } else {
                // Add empty user data if not found
                aiPayload.put("user", Map.of("id", userId));
            }
            
            // Add request metadata
            aiPayload.put("requestType", "productScan");
            aiPayload.put("timestamp", System.currentTimeMillis());
            
            // Set up headers for POST request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(aiPayload, headers);
            
            // Send to AI service (either directly or via recommendation service)
            String aiServiceUrl = "http://192.168.1.69:8000/api/analyze"; // Replace with actual AI service URL
            
            // Try to use the generalized recommendation with the product and user
            try {
                if (user != null) {
                    Map<String, Object> aiResponse = recommendationService.generateRecommendationWithType(user, produit);
                    System.out.println("Successfully generated recommendation via service: " + aiResponse.get("recommendation_type"));
                } else {
                    // Fall back to direct REST call when user is not available
                    ResponseEntity<Map> response = restTemplate.postForEntity(
                        aiServiceUrl, requestEntity, Map.class);
                    System.out.println("AI service direct response: " + response.getStatusCode());
                }
            } catch (Exception e) {
                System.out.println("Error generating recommendation: " + e.getMessage());
                
                // Don't crash the app - just log the error
                System.out.println("AI recommendation failed but continuing with app flow: " + e.getMessage());
            }
        } catch (Exception e) {
            // Log the error but don't crash the app
            System.out.println("Error sending data to AI service: " + e.getMessage());
            e.printStackTrace();
            System.out.println("Continuing with app flow despite AI service error");
        }
    }

    /**
     * Endpoint to check if a product exists by barcode
     */
    @GetMapping("/check/{barcode}")
    public ResponseEntity<?> checkProductExists(@PathVariable String barcode) {
        try {
            System.out.println("===== CHECK PRODUCT CONTROLLER DEBUG =====");
            System.out.println("Checking barcode: " + barcode + " (Type: " + barcode.getClass().getSimpleName() + ")");
            
            // Normalize barcode to ensure consistency
            Long normalizedBarcode = dataValidationService.normalizeBarcode(barcode);
            System.out.println("Normalized barcode: " + normalizedBarcode);
            
            if (normalizedBarcode == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("exists", false);
                response.put("reason", "invalid_barcode");
                return ResponseEntity.ok(response);
            }
            
            // Use findByCodeBarre and check if result is present
            boolean exists = produitService.findByCodeBarre(normalizedBarcode).isPresent();
            
            System.out.println("Product exists: " + exists);
            System.out.println("===== END CHECK PRODUCT CONTROLLER DEBUG =====");
            
            Map<String, Object> response = new HashMap<>();
            response.put("exists", exists);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error in checkProductExists: " + e.getMessage());
            e.printStackTrace();  // Print full stack trace for better debugging
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Error checking product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Alternative endpoint to check product existence using query parameter
     */
    @GetMapping("/check")
    public ResponseEntity<?> checkProductExistsParam(@RequestParam(name = "codeBarre", required = true) String barcode) {
        return checkProductExists(barcode);
    }
    
    /**
     * Alternative endpoint to get product using query parameter
     */
    @GetMapping("/product")
    public ResponseEntity<?> getProductByParam(@RequestParam(name = "codeBarre", required = true) String barcode) {
        return scanProductByBarcode(barcode, null);
    }
} 