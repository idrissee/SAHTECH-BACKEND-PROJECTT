package com.example.Sahtech.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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