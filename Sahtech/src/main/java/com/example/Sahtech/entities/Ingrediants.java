package com.example.Sahtech.entities;



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
    private Long idIngrediant;

    private String nomIngrediant;

    private Float Quantite;

    private Long produitId;// Référence à l'ID du produit
}
