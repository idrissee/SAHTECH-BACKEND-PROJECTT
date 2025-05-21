package com.example.Sahtech.Dto.Scan;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueScanDto {

    private String id;


    private Utilisateurs utilisateur;


    private Produit produit;

    private LocalDateTime dateScan;
    private String recommandationIA;
    private String recommendationType; // 'recommended', 'caution', or 'avoid'
   
} 

     