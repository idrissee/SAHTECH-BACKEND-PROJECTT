package com.example.Sahtech.Dto;

import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.entities.Utilisateurs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueScanDto {

    private String id;


    private Utilisateurs utilisateur;


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