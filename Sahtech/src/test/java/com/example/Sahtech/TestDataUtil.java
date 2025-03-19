package com.example.Sahtech;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.TypeProduit;
import com.example.Sahtech.entities.Additifs;
import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.entities.Produit;

public class TestDataUtil {

    public TestDataUtil() {}




    public static Produit creatTestProduitA() {
        return Produit.builder()
                .idProduit(1L)
                .nomProduit("rouiba")
                .typeProduit(TypeProduit.produitLate)
                .codeBarre(12365877L)
                .build();
    }

    public static Produit creatTestProduitB() {
        return Produit.builder()
                .idProduit(2L)
                .nomProduit("Gateau")
                .typeProduit(TypeProduit.produitLate)
                .codeBarre(12348796L)
                .build();
    }

    public static Produit creatTestProduitC() {
        return Produit.builder()
                .idProduit(3L)
                .nomProduit("milka")
                .typeProduit(TypeProduit.produitLate)
                .codeBarre(1236987L)
                .build();
    }

    public static Ingrediants createTestIngrediantsA() {
        return Ingrediants.builder()
                .idIngrediant(1L)
                .Quantite(5.0F)
                .nomIngrediant("salt")
                .build();
    }


    public static Ingrediants createTestIngrediantsB() {
        return Ingrediants.builder()
                .idIngrediant(2L)
                .Quantite(4.0F)
                .nomIngrediant("SUGAR")
                .build();
    }

    public static Ingrediants createTestIngrediantsC() {
        return Ingrediants.builder()
                .idIngrediant(3L)
                .Quantite(4.4F)
                .nomIngrediant("SODIUM")
                .build();
    }


    public static Additifs createTestAdditifsA() {
       return Additifs.builder()
               .idAdditif(1L)
               .maladieCause(Maladie.QLERGIAUE)
               .seuil(2F)
               .nomAdditif("sin1")
               .build();
    }
    public static Additifs createTestAdditifsB() {
        return Additifs.builder()
                .idAdditif(2L)
                .maladieCause(Maladie.DIABATE)
                .seuil(3F)
                .nomAdditif("sin2")
                .build();
    }
    public static Additifs createTestAdditifsC() {
        return Additifs.builder()
                .idAdditif(3L)
                .maladieCause(Maladie.DIABATE)
                .seuil(4F)
                .nomAdditif("sin3")
                .build();
    }




}
