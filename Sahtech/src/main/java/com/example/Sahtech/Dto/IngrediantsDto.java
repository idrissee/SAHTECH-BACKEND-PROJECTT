package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.NomIngrediants;
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

    private NomIngrediants nomIngrediant;
    private Float Quantite;

    private String produitId;// Référence à l'ID du produit
}
