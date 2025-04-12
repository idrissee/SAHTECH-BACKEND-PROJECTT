package com.example.Sahtech.entities;


import com.example.Sahtech.Enum.TypeProduit;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {

    @Id
    private String idProduit;  // MongoDB génère un ObjectId automatiquement

    private Long codeBarre;
    private String nomProduit;
    private TypeProduit typeProduit;
    private List<String> nomAdditif; // Liste des IDs des additifs associés
    private String marque;
    private LocalDateTime dateAjout;
    private String description;
    private String nutriScoreId; // Référence à l'ID du NutriScore associé
}


