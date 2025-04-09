package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PubliciteDto {
    private Long id;
    
    // Relations
    private Long partenaireId;
    
    // Informations de base
    private String titre;
    private String description;
    private String imageUrl;
    private String lienRedirection;
    private TypePublicite typePub;
    
    // État et statut
    private StatusPublicite statusPublicite;
    private EtatPublicite etatPublicite;
    
    // Période de validité
    private Date dateDebut;
    private Date dateFin;
    
    // Informations sur le sponsor
    private String nomEntreprise;
    
    // Budget et coûts
    private Double budget;
    private Double budgetJournalier;
    private Double coutParClic;
    private Double coutParImpression;
    
    // Statistiques
    private Long impressions;
    private Long clics;
    
    // Priorité d'affichage
    private Integer priorite;
}
