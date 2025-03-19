package com.example.Sahtech.entities;


import com.example.Sahtech.Enum.TypeProduit;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.List;

@Document(collection = "produits")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Produit {


    @Id
    private Long idProduit;  // MongoDB génère un ObjectId automatiquement (ou tu peux mettre ObjectId)

    private Long codeBarre;

    private String nomProduit;

    private TypeProduit typeProduit;// Ou `private TypeProduit typeProduit;` si tu veux garder l'énumération

    private List<Long> additifsIds; // Liste des IDs des additifs associés


}


