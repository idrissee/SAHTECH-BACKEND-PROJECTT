package com.example.Sahtech.entities;

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
    private Long id;
    
    private String nom;
    private String codeBarre;
    private String marque;
    private String categorie;
    private String description;
    private String imageUrl;
    
    // Informations NutriScore intégrées
    private ValeurNutriScore valeurNutriScore; // A, B, C, D, E
    private String descriptionNutriScore;

    


    
    // Composition
    private List<String> ingredients;
    private List<String> nomAdditif;
    

    

}


