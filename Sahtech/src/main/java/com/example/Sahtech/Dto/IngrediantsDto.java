package com.example.Sahtech.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IngrediantsDto {



    private String idIngrediant;

    private String nomIngrediant;
    private String nutriScore; // Texte descriptif du nutriscore (peut être supprimé si redondant)
    private Double energie100g; // en kcal
    private Double proteines100g;
    private Double glucides100g;
    private Double lipides100g;
    private Double fibres100g;
    private Double sel100g;
    private Double sucre100g;

    private Float Quantite;

    private Long produitId;// Référence à l'ID du produit
}
