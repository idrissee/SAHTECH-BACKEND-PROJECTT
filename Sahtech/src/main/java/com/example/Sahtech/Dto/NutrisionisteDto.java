package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutrisionisteDto {

    private String id;

    private String specialite;
    private String nom;
    private String prenom;
    private Long numTelephone;
    private String email;
    private Date dateDeNaissance;
    private Float taille;
    private String sexe;
    private Float poids;
    private Maladie maladie;
    private Objectif objectif;
    private String password;
    private String localisationId;
    private Boolean estVerifie;
    private String photoProfilUrl;
    private String photoAttestationFonctionUrl;
}
