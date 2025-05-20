package com.example.Sahtech.Dto.Pub;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import com.example.Sahtech.entities.Pub.Partenaire;
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
