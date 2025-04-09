package com.example.Sahtech.Dto;

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
    private Long id;  // ID du produit
    
    private String nom;
    private String codeBarre;
    private String marque;
    private String categorie;
    private String description;
    private String imageUrl;
    
    // Informations NutriScore intégrées
    private ValeurNutriScore valeurNutriScore;
    private String descriptionNutriScore;
    private Integer scoreNumerique;
    
    // Informations nutritionnelles
    private String nutriScore;  // Note: ce champ peut être redondant avec valeurNutriScore
    private Double energie100g;
    private Double proteines100g;
    private Double glucides100g;
    private Double lipides100g;
    private Double fibres100g;
    private Double sel100g;
    private Double sucre100g;
    
    // Composition
    private List<String> ingredients;
    private List<String> additifs;
    private List<String> allergenes;
    
    // Recommandations
    private String recommandationIA;
    private List<String> pointsPositifs;
    private List<String> pointsNegatifs;
    private String impactSante;
    
    // Métadonnées
    private Boolean estValide;
    private String sourceDonnees;
    private String paysOrigine;
    private String dateCreation;
    private String dateModification;
}
