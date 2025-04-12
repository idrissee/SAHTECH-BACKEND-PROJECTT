package com.example.Sahtech.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
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