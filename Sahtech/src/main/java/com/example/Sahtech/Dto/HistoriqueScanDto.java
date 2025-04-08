package com.example.Sahtech.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueScanDto {
    private Long id;
    private Long utilisateurId;
    private Long produitId;
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