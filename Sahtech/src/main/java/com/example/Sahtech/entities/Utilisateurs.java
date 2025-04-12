package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.Maladie;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private String adresse;
    private Date dateDeNaissence;
    private Maladie maladie;
    private Float poids;
    private Float taille;
    private Boolean sport;
    private String sexe;





}
