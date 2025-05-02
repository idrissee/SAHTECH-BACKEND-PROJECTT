package com.example.Sahtech.entities.Pub;

import com.example.Sahtech.Enum.StatutPartenaire;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Entité représentant un partenaire publicitaire dans le système SAHTECH
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "Partenaire")
public class Partenaire {
    @Id
    private String id;
    
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