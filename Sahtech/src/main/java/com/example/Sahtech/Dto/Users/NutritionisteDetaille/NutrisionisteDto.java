package com.example.Sahtech.Dto.Users.NutritionisteDetaille;

import com.example.Sahtech.Dto.Users.UtilisateursDto;
import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NutrisionisteDto extends UtilisateursDto {
    // Fields specific to nutritionist
    private String specialite;
    private String localisationId;
    private Boolean estVerifie;
    private String photoUrlDiplome;
    private Double latitude;
    private Double longitude;
    private String cabinetAddress;
    private List<String> proveAttestationType;
    private List<String> dailyActivities;

    // Constructor that sets the type
    public NutrisionisteDto(String id, String nom, String prenom, Long numTelephone,
                           String email, Date dateDeNaissance, List<Maladie> maladies,
                           Float poids, Float taille, Boolean sport, String sexe,
                           List<String> allergies, String password, List<String> objectives,
                           List<String> healthGoals, String provider, Boolean hasChronicDisease,
                           Boolean hasAllergies, String preferredLanguage,
                           List<String> physicalActivities, String photoUrl,
                           String specialite, String localisationId, Boolean estVerifie,
                           String photoUrlDiplome, Double latitude, Double longitude,
                           String cabinetAddress, List<String> proveAttestationType,
                           List<String> dailyActivities) {
        super();
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setNumTelephone(numTelephone);
        this.setEmail(email);
        this.setDateDeNaissance(dateDeNaissance);
        this.setMaladies(maladies);
        this.setPoids(poids);
        this.setTaille(taille);
        this.setSexe(sexe);
        this.setPassword(password);
        this.setProvider(provider);
        this.setPhotoUrl(photoUrl);
        this.setType("NUTRITIONIST");
        
        this.specialite = specialite;
        this.localisationId = localisationId;
        this.estVerifie = estVerifie;
        this.photoUrlDiplome = photoUrlDiplome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cabinetAddress = cabinetAddress;
        this.proveAttestationType = proveAttestationType;
        this.dailyActivities = dailyActivities;
    }
} 