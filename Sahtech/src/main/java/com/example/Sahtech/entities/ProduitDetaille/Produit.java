package com.example.Sahtech.entities.ProduitDetaille;


import com.example.Sahtech.Dto.ProduitDetaille.IngredientInfoDto;
import com.example.Sahtech.Enum.TypeProduit;
import com.example.Sahtech.Enum.ValeurNutriScore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "produits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    private TypeProduit typeProduit;

    // Composition
    private List<IngredientInfoDto> ingredients;
    private List<String> nomAdditif;
    
    // Additional explicit getter for typeProduit
    public TypeProduit getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(TypeProduit typeProduit) {
        this.typeProduit = typeProduit;
    }
    
    // Explicit getters and setters for valeurNutriScore
    public ValeurNutriScore getValeurNutriScore() {
        return valeurNutriScore;
    }

    public void setValeurNutriScore(ValeurNutriScore valeurNutriScore) {
        this.valeurNutriScore = valeurNutriScore;
    }
    
    // Explicit getter and setter for descriptionNutriScore
    public String getDescriptionNutriScore() {
        return descriptionNutriScore;
    }
    
    public void setDescriptionNutriScore(String descriptionNutriScore) {
        this.descriptionNutriScore = descriptionNutriScore;
    }
    
    // Explicit getters and setters for ingredients
    public List<IngredientInfoDto> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(List<IngredientInfoDto> ingredients) {
        this.ingredients = ingredients;
    }
}


