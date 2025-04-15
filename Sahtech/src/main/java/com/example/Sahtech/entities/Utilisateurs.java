package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Document(collection = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateurs {

    @Id
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
    private Objectif objectif;
    private Maladie maladie;
//    private List<String> historiqueScanIds;
//    private List<String> nutritionisteFavorisIds;

    public int getAge() {
        if (dateDeNaissance == null) return 0;
        LocalDate birthDate = new java.sql.Date(dateDeNaissance.getTime()).toLocalDate();
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    public Float getIMC() {
        if (poids == null || taille == null || taille == 0) return null;
        return poids / ((taille/100) * (taille/100));
    }
}
