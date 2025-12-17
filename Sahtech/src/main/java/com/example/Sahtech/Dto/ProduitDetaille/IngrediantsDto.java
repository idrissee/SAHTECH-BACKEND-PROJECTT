package com.example.Sahtech.Dto.ProduitDetaille;

import com.example.Sahtech.Enum.NomIngrediants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IngrediantsDto {

    private String idIngrediant;

    private NomIngrediants nomIngrediant;
    private String quantite;

    private String produitId;// Référence à l'ID du produit
}
