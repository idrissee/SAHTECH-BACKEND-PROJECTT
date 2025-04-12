package com.example.Sahtech.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngrediantsDto {
    private String idIngrediant;
    private String nomIngrediant;
    private Float quantite;
    private String produitId; // Référence à l'ID du produit
}
