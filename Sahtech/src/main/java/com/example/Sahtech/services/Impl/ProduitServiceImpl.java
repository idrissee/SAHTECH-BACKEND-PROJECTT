package com.example.Sahtech.services.Impl;


import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.repositories.ProduitRepository;
import com.example.Sahtech.services.ProduitService;
import org.springframework.stereotype.Service;

@Service
public class ProduitServiceImpl implements ProduitService {

    ProduitRepository produitRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }
}
