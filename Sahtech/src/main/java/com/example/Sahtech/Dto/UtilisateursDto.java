package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import lombok.*;


import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class UtilisateursDto {

    private Long id;
    private String nom;
    private String prenom;
    private Long telephone;
    private String email;
    private String adresse;
    private Date dateDeNaissence;
    private Maladie maladie;
    private Float poids;
    private Float taille;
    private Boolean sport;
    private String sexe;



}
