package com.example.Sahtech.Controllers.ProduitDetailleControllers;

import com.example.Sahtech.Enum.TypeProduit;
import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.NutriScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutriscore")
public class NutriScoreController {

    private final NutriScoreService nutriScoreService;

    @Autowired
    public NutriScoreController(NutriScoreService nutriScoreService) {
        this.nutriScoreService = nutriScoreService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ValeurNutriScore> calculateNutriScore(
            @RequestBody Produit produit,
            @RequestParam TypeProduit typeProduit) {
        ValeurNutriScore score = nutriScoreService.calculateNutriScore(produit, typeProduit);
        return ResponseEntity.ok(score);
    }
}
