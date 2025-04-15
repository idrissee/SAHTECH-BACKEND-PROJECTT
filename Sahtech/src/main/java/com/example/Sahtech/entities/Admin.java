package com.example.Sahtech.entities;


import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.Objectif;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    @Id
    private String id;
    private String role;
    private List<String> permissions;
    private String nom;
    private String prenom;
    private Long numTelephone;
    private String email;
    private Date dateDeNaissance;
    private Float taille;
    private String sexe;
    private Float poids;
    private Maladie maladie;
    private Objectif objectif;
    private String password;

}
