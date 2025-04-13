package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.TypeProduit;
import com.example.Sahtech.Enum.ValeurNutriScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProduitDto {

    private String id;

    private String nom;
    private Long codeBarre;
    private String marque;
    private String categorie;
    private String description;
    private String imageUrl;
    private TypeProduit typeProduit;

    // Informations NutriScore intégrées
    private ValeurNutriScore valeurNutriScore; // A, B, C, D, E
    private String descriptionNutriScore;





    // Composition
    private List<String> ingredients;
    private List<String> nomAdditif;
}
