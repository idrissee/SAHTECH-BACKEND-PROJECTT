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
    private Long telephone;
    private String email;
    private String adresse;
    private Date dateDeNaissence;


}
