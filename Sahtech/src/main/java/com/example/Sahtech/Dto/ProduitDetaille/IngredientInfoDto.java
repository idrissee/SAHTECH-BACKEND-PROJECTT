package com.example.Sahtech.Dto.ProduitDetaille;

import com.example.Sahtech.Enum.NomIngrediants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientInfoDto {
    private NomIngrediants nomIngrediant;
    private String quantite;
}
