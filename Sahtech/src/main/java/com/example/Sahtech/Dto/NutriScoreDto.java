package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.ValeurNutriScore;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutriScoreDto {
    
    private String id;
    private ValeurNutriScore valeur;
    private String description;
    private Integer scoreNumerique;
} 