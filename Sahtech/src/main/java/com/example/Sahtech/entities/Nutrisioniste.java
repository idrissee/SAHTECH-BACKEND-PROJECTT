package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "nutrisioniste")
public class Nutrisioniste {
    @Id
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String password;
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
