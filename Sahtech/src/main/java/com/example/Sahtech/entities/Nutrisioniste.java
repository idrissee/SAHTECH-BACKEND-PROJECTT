package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.Objectif;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "nutrisionistes")
public class Nutrisioniste {

    @Id
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
    private String password;
    private String localisationId;
    private Boolean estVerifie;
    
    // Updated: Changed from single enum to List of Strings to match Flutter frontend
    private List<String> maladies = new ArrayList<>();
    
    // Added: Match Flutter's allergies field
    private List<String> allergies = new ArrayList<>();
    
    // Keep for backward compatibility, but use objectives list for new data
    private Objectif objectif;
    
    // Added: Match Flutter UserModel fields
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
