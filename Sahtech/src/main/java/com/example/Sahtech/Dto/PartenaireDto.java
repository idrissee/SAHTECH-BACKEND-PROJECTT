package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.StatutPartenaire;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO pour l'entité Partenaire
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartenaireDto {
    private Long id;
    
    // Informations de base
    private String nom;
    private String domaineActivite;
    private String email;
    private String telephone;
    private String siteWeb;
    private String logo;
    
    // Dates et statut
    private Date dateInscription;
    private StatutPartenaire statut;
    
    // Informations financières
    private Double solde;
    
    // Informations supplémentaires
    private String description;
    private String conditions;
    private String referent;
} 