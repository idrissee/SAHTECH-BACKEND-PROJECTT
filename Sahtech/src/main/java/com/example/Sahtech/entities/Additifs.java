package com.example.Sahtech.entities;


import com.example.Sahtech.Enum.Maladie;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Additifs {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Additif-id-seq")
    private Long idAdditif;

    private String nomAdditif;

    private Float seuil;

    private String maladieCause;


    @ManyToOne(cascade = CascadeType.ALL)
    private Produit produit;




}
