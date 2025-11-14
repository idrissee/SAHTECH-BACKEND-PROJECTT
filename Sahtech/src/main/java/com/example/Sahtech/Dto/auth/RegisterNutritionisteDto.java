package com.example.Sahtech.Dto.auth;

import com.example.Sahtech.Enum.Maladie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterNutritionisteDto {
    // Basic User Info
    private String nom;
    private String prenom;
    private Long numTelephone;
    private String email;
    private String password;
    private Date dateDeNaissance;
    private String sexe;
    private Float poids;
    private Float taille;
    private Boolean sport;
    
    // Health Information
    private List<String> allergies;
    private List<Maladie> maladies;
    private Boolean hasChronicDisease;
    private Boolean hasAllergies;
    
    // Preferences
    private String preferredLanguage;
    private List<String> objectives;
    private List<String> healthGoals;
    private List<String> physicalActivities;
    
    // Professional Information
    private String specialite;
    private String cabinetAddress;
    private Double latitude;
    private Double longitude;
    private List<String> proveAttestationType;
    private List<String> dailyActivities;
    private String photoUrlDiplome;
    
    // Additional Fields
    private String photoUrl;
    private String provider = "LOCAL"; // Default value
} 