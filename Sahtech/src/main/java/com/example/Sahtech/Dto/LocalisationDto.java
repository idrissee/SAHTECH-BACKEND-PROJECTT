package com.example.Sahtech.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class LocalisationDto {
    
    private Long id;
    
    private String ville;
    
    private String pays;
    
    private String region;
    
    private String adresse;
    
    private Double latitude;
    
    private Double longitude;
    
    private String codePostal;
    
    private String description;
} 