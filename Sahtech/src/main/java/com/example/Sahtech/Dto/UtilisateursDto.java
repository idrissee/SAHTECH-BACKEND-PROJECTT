package com.example.Sahtech.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UtilisateursDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String role;
    private Date dateDeNaissance;
    private String profileId;
}
