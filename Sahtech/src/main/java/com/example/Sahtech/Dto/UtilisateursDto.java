package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class UtilisateursDto {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private Long numTelephone;
    private String password;
    private Date dateDeNaissance;
    private Float taille;
    private Float poids;
    private String sexe;
    private Maladie maladie;
    private Objectif objectif;
    private String provider;
    private String type;
    private Long countScans = 0L;
}

