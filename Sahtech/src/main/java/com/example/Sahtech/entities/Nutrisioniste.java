package com.example.Sahtech.entities;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "nutritionistes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nutrisioniste {

    @Id
    private String id;

    private String nom;
    private String prenom;
    private String adresse;
    private Date dateDeNaissence;

    private String specialite;
    private String description;
    private String expertise;
    private String formation;
    private String experience;
    private String localisationId;
    private String imageProfil;
    private Boolean estVerifie;
    private Float noteMoyenne;
    private Integer nombreAvis;
    private String telephone;
    private String email;
    private String siteweb;
}
