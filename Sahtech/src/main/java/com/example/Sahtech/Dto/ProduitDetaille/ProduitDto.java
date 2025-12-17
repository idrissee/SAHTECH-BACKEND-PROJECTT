package com.example.Sahtech.Dto.ProduitDetaille;

import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.Enum.TypeProduit;
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
    private String codeBarre;
    private String marque;
    private String categorie;
    private String description;
    private String quantite;
    private String imageUrl;

    // Informations NutriScore intégrées
    private ValeurNutriScore valeurNutriScore; // A, B, C, D, E
    private String descriptionNutriScore;

    private TypeProduit typeProduit;

    public void setTypeProduit(String typeProduit) {
        if (typeProduit == null || typeProduit.trim().isEmpty()) {
            this.typeProduit = null; // Or throw an exception, depending on desired behavior
        } else {
            try {
                this.typeProduit = TypeProduit.valueOf(typeProduit.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                 // Handle invalid value, maybe set to null or throw exception
                 System.err.println("Invalid TypeProduit value: " + typeProduit);
                 this.typeProduit = null; // Set to null if invalid
            }
        }
    }

    // Composition
    private List<IngredientInfoDto> ingredients;
    private List<String> nomAdditif;

    public ValeurNutriScore getValeurNutriScore() {
        return valeurNutriScore;
    }

    public void setValeurNutriScore(ValeurNutriScore valeurNutriScore) {
        this.valeurNutriScore = valeurNutriScore;
    }
}
