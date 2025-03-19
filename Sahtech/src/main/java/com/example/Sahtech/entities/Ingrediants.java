package com.example.Sahtech.entities;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;


@Document(collection = "ingredients")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ingrediants {

    private Long idIngrediant;

    private String nomIngrediant;

    private Float Quantite;

    private String produitId;// Référence à l'ID du produit
}