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
    private Integer scoreNumerique;
    
    // Informations nutritionnelles
    private String nutriScore; // Texte descriptif du nutriscore (peut être supprimé si redondant)
    private Double energie100g; // en kcal
    private Double proteines100g;
    private Double glucides100g;
    private Double lipides100g;
    private Double fibres100g;
    private Double sel100g;
    private Double sucre100g;
    
    // Composition
    private List<String> ingredients;
    private List<String> nomAdditif;
    private List<String> allergenes;
    
    // Recommandations
    private String recommandationIA;
    private List<String> pointsPositifs;
    private List<String> pointsNegatifs;
    private String impactSante; // Bonne, Moyenne, Mauvaise
    
    // Métadonnées
    private Boolean estValide;
    private String sourceDonnees; // "SAHTECH", "OPEN_FOOD_FACTS", etc.
    private String paysOrigine;
    private String dateCreation;
    private String dateModification;
}


