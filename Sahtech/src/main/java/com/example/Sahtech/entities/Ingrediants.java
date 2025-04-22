package com.example.Sahtech.entities;



import com.example.Sahtech.Enum.NomIngrediants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




@Document(collection = "ingredients")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ingrediants {

    @Id
    private String idIngrediant;

    private NomIngrediants nomIngrediant;
    private String quantite;

    private String produitId;// Référence à l'ID du produit
}
