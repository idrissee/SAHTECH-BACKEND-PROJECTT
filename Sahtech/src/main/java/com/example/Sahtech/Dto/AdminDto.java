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
public class AdminDto {

    private String id;
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

}
