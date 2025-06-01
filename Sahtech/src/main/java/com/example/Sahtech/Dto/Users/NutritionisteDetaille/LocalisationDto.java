package com.example.Sahtech.Dto.Users.NutritionisteDetaille;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LocalisationDto {


    private String id;

    private String ville;

    private String pays;

    private String region;

    private String adresse;

    private Double latitude;

    private Double longitude;

    private String codePostal;

    private String description;
} 