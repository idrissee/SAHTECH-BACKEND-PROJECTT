package com.example.Sahtech.services;

import com.example.Sahtech.entities.Produit;

import java.util.List;
import java.util.Optional;

public interface ProduitService {

    Produit save(Produit  produit);

    Produit createProduit(Produit produit);

    List<Produit> findAll();

    Optional<Produit> findOnebyId(Long id);

    boolean isExists(Long id);
}
