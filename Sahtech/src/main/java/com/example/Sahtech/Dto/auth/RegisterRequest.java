package com.example.Sahtech.Dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    
    // Health profile data
    private String dateDeNaissance; // Format: yyyy-MM-dd
    private Float poids;
    private Float taille;
    
    // Health flags
    private Boolean hasChronicDisease = false;
    private Boolean hasAllergies = false;
    private String preferredLanguage;
    private Boolean doesExercise = false;
    private String activityLevel;
    
    // Health data lists
    private List<String> maladies = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();
    private List<String> objectives = new ArrayList<>(); // Main list for all health objectives/goals
    
    // Profile image URL - explicitly mark for Jackson
    @JsonProperty("photoUrl")
    private String photoUrl;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RegisterRequest{\n");
        sb.append("  nom='").append(nom).append("',\n");
        sb.append("  prenom='").append(prenom).append("',\n");
        sb.append("  email='").append(email).append("',\n");
        sb.append("  password='[REDACTED]',\n");
        sb.append("  telephone=").append(telephone).append(",\n");
        sb.append("  userType='").append(userType).append("',\n");
        sb.append("  dateDeNaissance='").append(dateDeNaissance).append("',\n");
        sb.append("  poids=").append(poids).append(",\n");
        sb.append("  taille=").append(taille).append(",\n");
        sb.append("  hasChronicDisease=").append(hasChronicDisease).append(",\n");
        sb.append("  hasAllergies=").append(hasAllergies).append(",\n");
        sb.append("  preferredLanguage='").append(preferredLanguage).append("',\n");
        sb.append("  doesExercise=").append(doesExercise).append(",\n");
        sb.append("  activityLevel='").append(activityLevel).append("',\n");
        sb.append("  maladies=").append(maladies).append(",\n");
        sb.append("  allergies=").append(allergies).append(",\n");
        sb.append("  objectives=").append(objectives).append(",\n");
        sb.append("  photoUrl='").append(photoUrl).append("'\n");
        sb.append("}");
        return sb.toString();
    }
}


