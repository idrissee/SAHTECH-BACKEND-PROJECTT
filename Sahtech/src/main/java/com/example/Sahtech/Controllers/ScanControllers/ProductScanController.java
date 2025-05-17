package com.example.Sahtech.Controllers.ScanControllers;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/API/Sahtech/scan")
@RequiredArgsConstructor
public class ProductScanController {

    private final ProduitService produitService;

    /**
     * Endpoint for scanning product by barcode
     */
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<?> scanProductByBarcode(@PathVariable String barcode) {
        try {
            System.out.println("===== PRODUCT SCAN CONTROLLER DEBUG =====");
            System.out.println("Received barcode: " + barcode + " (Type: " + barcode.getClass().getSimpleName() + ")");
            
            // Attempt to find the product by barcode
            Optional<Produit> produit = produitService.findByCodeBarre(barcode);
            System.out.println("Product found: " + produit.isPresent());
            
            // If not found, return not found response
            if (produit.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("status", "not_found");
                response.put("message", "Product not found with barcode: " + barcode);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            System.out.println("Returning product with ID: " + produit.get().getId());
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
     * Endpoint to check if a product exists by barcode
     */
    @GetMapping("/check/{barcode}")
    public ResponseEntity<?> checkProductExists(@PathVariable String barcode) {
        try {
            System.out.println("===== CHECK PRODUCT CONTROLLER DEBUG =====");
            System.out.println("Checking barcode: " + barcode + " (Type: " + barcode.getClass().getSimpleName() + ")");
            
            // Use findByCodeBarre and check if result is present
            boolean exists = produitService.findByCodeBarre(barcode).isPresent();
            
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
} 