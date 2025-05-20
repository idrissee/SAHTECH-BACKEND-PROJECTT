package com.example.Sahtech.entities.ProduitDetaille;


import com.example.Sahtech.Dto.ProduitDetaille.IngredientInfoDto;
import com.example.Sahtech.Enum.ValeurNutriScore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    // Primary field is codeBarre (Long type for large numeric values)
    private Long codeBarre;
    
    // Alternative field name for API consistency
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getBarcode() {
        return codeBarre != null ? codeBarre.toString() : null;
    }
    
    // Handle incoming barcode values from Flutter
    @JsonProperty("barcode")
    public void setBarcode(String barcode) {
        try {
            if (barcode != null && !barcode.isEmpty()) {
                this.codeBarre = Long.parseLong(barcode);
            }
        } catch (NumberFormatException e) {
            // Log error but don't throw exception to prevent API failures
            System.err.println("Invalid barcode format: " + barcode);
        }
    }

    private String nom;
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


