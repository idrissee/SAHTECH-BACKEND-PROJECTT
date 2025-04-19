package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.ProduitDto;
import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.ProduitService;
import com.example.Sahtech.services.HistoriqueScanService;
import com.example.Sahtech.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Produits")
public class ProduitController {

    private ProduitService produitService;
    private Mapper<Produit, ProduitDto> produitMapper;
    
    @Autowired
    private HistoriqueScanService historiqueScanService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ProduitController(ProduitService produitService, 
                            Mapper<Produit, ProduitDto> produitMapper) {
        this.produitService = produitService;
        this.produitMapper = produitMapper;
    }

    @PostMapping()
    public ResponseEntity<ProduitDto> createProduit(@RequestBody ProduitDto produitDto) {
        Produit produit = produitMapper.mapFrom(produitDto);
        Produit savedProduit = produitService.createProduit(produit);
        return new ResponseEntity<>(produitMapper.mapTo(savedProduit), HttpStatus.CREATED);
    }

    @GetMapping(path ="/All")
    public List<ProduitDto> listProduits() {
            List<Produit> produits  = produitService.findAll();
           return  produits.stream()
                   .map(produitMapper::mapTo)
                   .collect(Collectors.toList());
    }

    @GetMapping(path ="/scanned/count")
    public ResponseEntity<Map<String, Integer>> getScannedProductsCount(HttpServletRequest request) {
        // Extraire l'ID de l'utilisateur du token JWT
        String token = extractToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Obtenir le nombre de produits scannés par l'utilisateur
        int count = historiqueScanService.countScannedProductsByUser(userId);
        
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path ="/{id}")
    public ResponseEntity<ProduitDto> getProduit(@PathVariable("id") String id){
        Optional<Produit> foundproduit = produitService.findOnebyId(id);
        return foundproduit.map(produit-> {
            ProduitDto produitDto = produitMapper.mapTo(produit);
            return new ResponseEntity<>(produitDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProduitDto> fullUpdateProduit(
            @PathVariable("id") String id,
            @RequestBody ProduitDto produitDto) {

        if(!produitService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        produitDto.setId(id);
        Produit produit = produitMapper.mapFrom(produitDto);
        Produit savedProduit = produitService.save(produit);
        return new ResponseEntity<>(
                produitMapper.mapTo(savedProduit)
                , HttpStatus.OK);
    }

    @PatchMapping(path ="/{id}")
    public ResponseEntity<ProduitDto> partialUpdateProduit(
            @PathVariable("id") String id,
            @RequestBody ProduitDto produitDto){

        if(!produitService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Produit produit = produitMapper.mapFrom(produitDto);
        Produit updatedProduit = produitService.partialUpdate(id,produit);
        return new ResponseEntity<>(
                produitMapper.mapTo(updatedProduit),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteProduit(@PathVariable("id") String id){
        produitService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping(path = "/{id}/nutriscore")
    public ResponseEntity<ValeurNutriScore> getNutriScoreForProduit(@PathVariable("id") String id) {
        // Récupérer le produit
        Optional<Produit> foundProduit = produitService.findOnebyId(id);
        
        if (foundProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Produit produit = foundProduit.get();
        
        // Vérifier si le produit a un NutriScore
        if (produit.getValeurNutriScore() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Retourner directement la valeur du NutriScore
        return new ResponseEntity<>(produit.getValeurNutriScore(), HttpStatus.OK);
    }
    
    @PutMapping(path = "/{id}/nutriscore/{valeurNutriScore}")
    public ResponseEntity<ProduitDto> setNutriScoreForProduit(
            @PathVariable("id") String id,
            @PathVariable("valeurNutriScore") ValeurNutriScore valeurNutriScore) {
        
        // Vérifier que le produit existe
        if (!produitService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Récupérer le produit
        Optional<Produit> foundProduit = produitService.findOnebyId(id);
        
        if (foundProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Produit produit = foundProduit.get();
        
        // Mettre à jour le NutriScore du produit
        produit.setValeurNutriScore(valeurNutriScore);
        
        Produit updatedProduit = produitService.save(produit);
        
        return new ResponseEntity<>(produitMapper.mapTo(updatedProduit), HttpStatus.OK);
    }
    
    @PostMapping("/{id}/recommendation")
    public ResponseEntity<Map<String, String>> saveRecommendation(
            @PathVariable("id") String productId,
            @RequestBody Map<String, String> recommendationRequest,
            HttpServletRequest request) {
        
        // Extraire l'ID de l'utilisateur du token JWT
        String token = extractToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Vérifier que le produit existe
        if (!produitService.isExists(productId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        String recommendation = recommendationRequest.get("recommendation");
        if (recommendation == null || recommendation.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        // Enregistrer la recommandation
        boolean saved = produitService.saveRecommendation(productId, userId, recommendation);
        
        if (saved) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Recommendation saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}/recommendation")
    public ResponseEntity<Map<String, String>> getRecommendation(
            @PathVariable("id") String productId,
            HttpServletRequest request) {
            
        // Extraire l'ID de l'utilisateur du token JWT
        String token = extractToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Vérifier que le produit existe
        if (!produitService.isExists(productId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Récupérer la recommandation
        String recommendation = produitService.getRecommendation(productId, userId);
        
        if (recommendation != null) {
            Map<String, String> response = new HashMap<>();
            response.put("recommendation", recommendation);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Helper method to extract the JWT token from the request
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
