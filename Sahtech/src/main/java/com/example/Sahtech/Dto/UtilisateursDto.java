package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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


}
