package com.example.Sahtech.services.Interfaces.ProduitDetaille;

import com.example.Sahtech.Enum.TypeProduit;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.Enum.ValeurNutriScore;

public interface NutriScoreService {
    ValeurNutriScore calculateNutriScore(Produit produit, TypeProduit typeProduit);
}
