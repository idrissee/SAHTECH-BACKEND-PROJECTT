package com.example.Sahtech.entities;


import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Documented;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table
@Entity
public class Ingrediants {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Ingrediant-id-seq")
    private Long idIngrediant;

    private String nomIngrediant;

    private Float Quantite;

    @ManyToOne(cascade = CascadeType.ALL)
    private Produit produit;

}
