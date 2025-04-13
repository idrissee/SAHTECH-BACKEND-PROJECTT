package com.example.Sahtech.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

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