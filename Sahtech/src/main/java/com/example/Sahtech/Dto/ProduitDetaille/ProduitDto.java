package com.example.Sahtech.Dto.ProduitDetaille;

import com.example.Sahtech.Enum.ValeurNutriScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String quantite;
    private String imageUrl;

    // Informations NutriScore intégrées
    private ValeurNutriScore valeurNutriScore; // A, B, C, D, E
    private String descriptionNutriScore;





    // Composition
    private List<IngredientInfoDto> ingredients;
    private List<String> nomAdditif;
}
