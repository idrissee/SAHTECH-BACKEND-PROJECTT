package com.example.Sahtech.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueScanDto {
    private String id;
    private String utilisateurId;
    private String produitId;
    private LocalDateTime dateScan;
    private String noteNutriScore;
    private String recommandationIA;
    private List<String> additifsDetectes;
    private List<String> ingredients;
    private List<String> pointsPositifs;
    private List<String> pointsNegatifs;
    private String impactSante;
    private String commentaireUtilisateur;
    private Boolean estFavori;
} 