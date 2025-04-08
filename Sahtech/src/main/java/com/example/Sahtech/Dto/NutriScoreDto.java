package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.ValeurNutriScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NutriScoreDto {
    
    private Long id;
    
    private ValeurNutriScore valeur;
    
    private String description;
    
    private Integer scoreNumerique;
} 