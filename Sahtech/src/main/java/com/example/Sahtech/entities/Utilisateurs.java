package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.Maladie;
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
    private String adresse;
    private Date dateDeNaissence;
    private Maladie maladie;


}
