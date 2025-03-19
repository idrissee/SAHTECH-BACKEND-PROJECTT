package com.example.Sahtech.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IngrediantsDto {


    private Long idIngrediant;

    private String nomIngrediant;

    private Float Quantite;

    private Long produitId;// Référence à l'ID du produit
}
