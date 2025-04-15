package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {

    private String id;
    private String role;
    private List<String> permissions;
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

