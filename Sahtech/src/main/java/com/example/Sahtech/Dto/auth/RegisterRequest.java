package com.example.Sahtech.Dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    
    @NotNull(message = "Le numéro de téléphone est obligatoire")
    private Long telephone;
    
    @NotBlank(message = "Le type d'utilisateur est obligatoire")
    private String userType; // USER, ADMIN, NUTRITIONIST
    
    // Added additional fields for health data
    private Boolean hasChronicDisease;
    private Boolean hasAllergies;
    private String preferredLanguage;
    private Boolean doesExercise;
    private String activityLevel;
    private List<String> maladies = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();
    private List<String> objectives = new ArrayList<>();
    private List<String> physicalActivities = new ArrayList<>();
    private List<String> dailyActivities = new ArrayList<>();
    private List<String> healthGoals = new ArrayList<>();
}


}