package com.example.Sahtech.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingrediants {

    @Id
    private String idIngrediant;
    private String nomIngrediant;
    private Float quantite;
    private String produitId; // Référence à l'ID du produit
}
