package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "nutritionistes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Nutrisioniste extends Personne {
    
    @Id
    private Long id;
    
    private String specialite;
    
    private Integer anneesExperience;
    
    private Long localisationId; // ID de la localisation du nutritionniste
    
    private String certifications;
    
    private String biographie;
    
    private List<Long> recommendationsIds; // IDs des recommandations formulées
    
    private String numeroTelephone;
    
    private Boolean estVerifie; // Si le nutritionniste est vérifié par l'administration
}
