package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import com.example.Sahtech.entities.Partenaire;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PubliciteDto {


    private String id;

    // Relations

    private Partenaire partenaire;
    private Long partenaire_id;

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

}
