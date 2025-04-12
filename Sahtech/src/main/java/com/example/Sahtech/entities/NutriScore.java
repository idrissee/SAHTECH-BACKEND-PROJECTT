package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.ValeurNutriScore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nutriscores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutriScore {
    
    @Id
    private String id;
    
    private ValeurNutriScore valeur; // A, B, C, D, E
    
    private String description;
    
    private Integer scoreNumerique; // Score numérique correspondant à la lettre
} 