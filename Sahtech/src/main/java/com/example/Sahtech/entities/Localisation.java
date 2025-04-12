package com.example.Sahtech.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "localisations")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class Localisation {
    
    @Id
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