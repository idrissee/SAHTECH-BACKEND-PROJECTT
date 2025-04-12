package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.TypeProduit;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduitDto {

    private String idProduit;  // MongoDB génère un ObjectId automatiquement
    private Long codeBarre;
    private String nomProduit;
    private TypeProduit typeProduit;
    private List<String> nomAdditif;  // Liste des IDs des additifs associés
    private String marque;
    private LocalDateTime dateAjout;
    private String description;
    private String nutriScoreId; // ID du NutriScore associé
}
