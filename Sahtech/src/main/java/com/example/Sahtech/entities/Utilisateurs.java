package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Document(collection = "utilisateurs")
public class Utilisateurs {

    @Id
    private String id;

    private String nom;
    private String prenom;
    private Long numTelephone;
    private String email;
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
    private String provider; // LOCAL, GOOGLE
    private Long countScans = 0L;
    private List<String> objectives;
    private List<String> dailyActivities;
    private List<String> physicalActivities;
    private List<String> healthGoals;
    private boolean hasChronicDisease;
    private boolean hasAllergies;
     private String preferredLanguage;

    // Champ pour distinguer le type d'utilisateur
    private String type;

    // Constructeur protégé pour les sous-classes
    protected Utilisateurs(String type) {
        this.type = type;
    }
    private List<String> historiqueScanIds;
    private List<String> nutritionisteFavorisIds;
    private String photoUrl; // lien Cloudinary


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
