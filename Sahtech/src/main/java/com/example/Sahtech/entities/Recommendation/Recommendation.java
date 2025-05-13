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
    
    public Recommendation(Utilisateurs utilisateur, Produit produit, String content) {
        this.utilisateur = utilisateur;
        this.produit = produit;
        this.content = content;
    }
} 