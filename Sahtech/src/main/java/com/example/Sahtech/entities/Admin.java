package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "admin")
public class Admin {
    @Id
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String password;
    private String role;
}
