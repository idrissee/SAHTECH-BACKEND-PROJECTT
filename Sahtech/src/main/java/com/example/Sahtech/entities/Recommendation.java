package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
    
    private LocalDateTime createdAt;
    
    public Recommendation(Utilisateurs utilisateur, Produit produit, String content) {
        this.utilisateur = utilisateur;
        this.produit = produit;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
} 