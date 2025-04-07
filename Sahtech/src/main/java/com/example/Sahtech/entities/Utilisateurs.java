package com.example.Sahtech.entities;

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
    private String email;
    private String adresse;
    private String password;
    private String role;
    private Date dateDeNaissance;
    private String profileId; // Pour lier à un profil (admin ou nutritionniste)
}
