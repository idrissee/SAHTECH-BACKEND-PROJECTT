package com.example.Sahtech.services;

import com.example.Sahtech.entities.Produit;

import java.util.List;
import java.util.Optional;

public interface ProduitService {

    Produit save(Produit  produit);

    Produit createProduit(Produit produit);

    List<Produit> findAll();

    Optional<Produit> findOnebyId(String id);

    Optional<Produit> findByCodeBarre(String codeBarre);

    boolean isExists(String id);

    Produit partialUpdate(String id, Produit produit);

    void delete(String id);

    Produit setPhotoUrl(String id, String photoUrl);
}