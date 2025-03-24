package com.example.Sahtech.services;

import com.example.Sahtech.entities.Produit;

import java.util.List;

public interface ProduitService {

    Produit createProduit(Produit  produit);

    List<Produit> findAll();
}
