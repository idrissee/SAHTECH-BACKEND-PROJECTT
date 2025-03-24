package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.TypeProduit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProduitDto {



    private Long idProduit;  // MongoDB génère un ObjectId automatiquement (ou tu peux mettre ObjectId)

    private Long codeBarre;

    private String nomProduit;

    private TypeProduit typeProduit;// Ou `private TypeProduit typeProduit;` si tu veux garder l'énumération

    private List<String> nomAdditif;  // Liste des IDs des additifs associés

    private String marque;

    private LocalDateTime dateAjout;

    private String description;

}
