package com.example.Sahtech.entities;


import com.example.Sahtech.Enum.TypeProduit;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table
public class Produit {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Produit-id-seq")
    private Long idProduit;

    private Long codeBarre;

    private String nomProduit;

    private String typeProduit;






}
