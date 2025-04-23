package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.time.LocalDateTime;

@Document(collection = "produit_recommendations")
@CompoundIndex(def = "{'produitId': 1, 'utilisateurId': 1}", unique = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProduitRecommendation {
    
    @Id
    private String id;
    
    private String produitId;
    
    private String utilisateurId;
    
    private String recommendation;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 