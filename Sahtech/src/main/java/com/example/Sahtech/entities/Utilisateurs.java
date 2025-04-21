package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Document(collection = "utilisateurs")
public class Utilisateurs {

    @Id
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private Long numTelephone;
    private String password;
    private Date dateDeNaissance;
    private Float taille;
    private Float poids;
    private String sexe;
    private Maladie maladie;
    private Objectif objectif;
    private String provider; // LOCAL, GOOGLE
    private Long countScans = 0L;
    
    // Champ pour distinguer le type d'utilisateur
    private String type;
    
    // Constructeur protégé pour les sous-classes
    protected Utilisateurs(String type) {
        this.type = type;
    }
}
