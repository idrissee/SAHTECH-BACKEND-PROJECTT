package com.example.Sahtech.entities.Scan;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "HistoriqueScann")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueScan {
    @Id
     private String id;

@DBRef
    private Utilisateurs utilisateur;

@DBRef
    private Produit produit;

    private LocalDateTime dateScan;
    private String recommandationIA;
    private String recommendationType; // 'recommended', 'caution', or 'avoid'
   

    
} 