package com.example.Sahtech.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NutrisionisteDto {
    
    private Long id;
    
    private String nom;
    
    private String prenom;
    
    private String email;
    
    private String adresse;
    
    private String role;
    
    private String specialite;
    
    private Integer anneesExperience;
    
    private Long localisationId;
    
    private List<String> certifications;
    
    private String biographie;
    
    private List<Long> recommendationsIds;
    
    private String numeroTelephone;
    
    private Boolean estVerifie;
}
