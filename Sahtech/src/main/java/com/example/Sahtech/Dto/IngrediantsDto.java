package com.example.Sahtech.Dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class IngrediantsDto {


    private Long idIngrediant;

    private String nomIngrediant;

    private Float Quantite;

    private Long produitId;// Référence à l'ID du produit
}
