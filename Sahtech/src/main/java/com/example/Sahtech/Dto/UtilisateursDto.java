package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateursDto {

    private String id;
    private String nom;
    private String prenom;
    private Long numTelephone;
    private String email;
    private String adresse;
    private Date dateDeNaissance;
    private List<Maladie> maladies;
    private Float poids;
    private Float taille;
    private String niveauActivite;
    private Boolean sport;
    private String sexe;
    private List<String> allergies;
    private String password;
    private List<String> historiqueScanIds;
    private List<String> nutritionisteFavorisIds;
    private String photoUrl; // lien Cloudinary
}
