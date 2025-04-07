package com.example.Sahtech.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String role;
}
