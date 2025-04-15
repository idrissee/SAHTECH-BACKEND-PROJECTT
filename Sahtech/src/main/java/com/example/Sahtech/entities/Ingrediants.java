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
