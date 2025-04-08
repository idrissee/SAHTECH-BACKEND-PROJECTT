package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.ValeurNutriScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nutriscores")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NutriScore {
    
    @Id
    private Long id;
    
    private ValeurNutriScore valeur; // A, B, C, D, E
    
    private String description;
    
    private Integer scoreNumerique; // Score numérique correspondant à la lettre
} 