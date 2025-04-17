package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "localisations")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Localisation {
    
    @Id
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