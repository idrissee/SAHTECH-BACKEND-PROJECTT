package com.example.Sahtech.Dto;


import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
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
    private List<String> maladies = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();
    private Objectif objectif;
    private String password;
    private String localisationId;
    private Boolean estVerifie;
    private List<String> objectives = new ArrayList<>();
    private Boolean hasChronicDisease;
    private String preferredLanguage;
    private Boolean doesExercise;
    private String activityLevel;
    private List<String> physicalActivities = new ArrayList<>();
    private List<String> dailyActivities = new ArrayList<>();
    private List<String> healthGoals = new ArrayList<>();
    private Boolean hasAllergies;
    private String allergyYear;
    private String allergyMonth;
    private String allergyDay;
    private String weightUnit;
    private String heightUnit;
    private String profileImageUrl;
}
