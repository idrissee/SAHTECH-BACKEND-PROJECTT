package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "utilisateurs")
public class Utilisateurs {

    @Id
    private Long id;
    private String nom;
    private String prenom;
    private Long telephone;
    private String email;
    private Date dateDeNaissence;
    private Float taille;
    private String sexe;
    private Float poids;
    private Maladie maladie;
    private Objectif objectif;


}
