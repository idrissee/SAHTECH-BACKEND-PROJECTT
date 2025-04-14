package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "historique_scans")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoriqueScan {
    @Id
    private String id;

    @DBRef
    private Utilisateurs utilisateur;

    @DBRef
    private Produit produit;

    private LocalDateTime dateScan;
    private String noteNutriScore; // A, B, C, D, E
    private String recommandationIA;
    private List<String> additifsDetectes;
    private List<String> ingredients;
    private List<String> pointsPositifs;
    private List<String> pointsNegatifs;
    private String impactSante; // Bonne, Moyenne, Mauvaise
    private String commentaireUtilisateur;
} 