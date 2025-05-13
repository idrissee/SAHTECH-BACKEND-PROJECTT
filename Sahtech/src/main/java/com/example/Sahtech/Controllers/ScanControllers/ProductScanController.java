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
            // Attempt to find the product by barcode
            Optional<Produit> produit = produitService.findByCodeBarre(barcode);
            
            if (produit.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("status", "not_found");
                response.put("message", "Product not found with barcode: " + barcode);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            return ResponseEntity.ok(produit.get());
        } catch (Exception e) {
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
            // Use findByCodeBarre and check if result is present
            boolean exists = produitService.findByCodeBarre(barcode).isPresent();
            
            Map<String, Object> response = new HashMap<>();
            response.put("exists", exists);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Error checking product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 