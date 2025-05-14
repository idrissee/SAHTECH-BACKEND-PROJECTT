package com.example.Sahtech.entities.Recommendation;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recommendations")
public class Recommendation {
    @Id
    private String id;
    
    @DBRef
    private Utilisateurs utilisateur;
    
    @DBRef
    private Produit produit;
    
    private String content;
    
    // Added field for recommendation type: 'recommended', 'caution', or 'avoid'
    private String recommendationType;
    
    public Recommendation(Utilisateurs utilisateur, Produit produit, String content) {
        this.utilisateur = utilisateur;
        this.produit = produit;
        this.content = content;
        this.recommendationType = "caution"; // Default value
    }
    
    public Recommendation(Utilisateurs utilisateur, Produit produit, String content, String recommendationType) {
        this.utilisateur = utilisateur;
        this.produit = produit;
        this.content = content;
        this.recommendationType = recommendationType;
    }
    
    // Getter for recommendationType that matches the name used in the controller
    public String getType() {
        return this.recommendationType;
    }
} 