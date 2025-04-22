package com.example.Sahtech.entities;


import com.example.Sahtech.Dto.IngredientInfoDto;
import com.example.Sahtech.Enum.TypeProduit;
import com.example.Sahtech.Enum.ValeurNutriScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "produits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    @Id
    private String id;

    private Long codeBarre;

    private String nom;
    private String marque;
    private String categorie;
    private String description;
    private String quantite;
    private String imageUrl;
    private TypeProduit typeProduit;
    
    // Informations NutriScore intégrées
    private ValeurNutriScore valeurNutriScore; // A, B, C, D, E
    private String descriptionNutriScore;

    // Composition
    private List<IngredientInfoDto> ingredients;
    private List<String> nomAdditif;
    

}


