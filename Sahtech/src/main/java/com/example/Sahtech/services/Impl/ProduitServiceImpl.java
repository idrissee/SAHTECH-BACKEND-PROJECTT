package com.example.Sahtech.services.Impl;


import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.repositories.AdditifsRepository;
import com.example.Sahtech.repositories.ProduitRepository;
import com.example.Sahtech.services.ProduitService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProduitServiceImpl implements ProduitService {

    ProduitRepository produitRepository;
    private final AdditifsRepository additifRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository, AdditifsRepository additifRepository) {
        this.produitRepository = produitRepository;
        this.additifRepository = additifRepository;
    }

    @Override
    public Produit createProduit(Produit produit) {
        Produit savedProduit = produitRepository.save(produit);

        // Step 2: For each additif, update its list of product IDs
        for (String nomAdditifs : produit.getNomAdditif()) {
            additifRepository.findByNomAdditif(nomAdditifs).ifPresent(additif -> {
                if (additif.getProduitsIds() == null) {
                    additif.setProduitsIds(new ArrayList<>());
                }

                // Avoid duplicates
                if (!additif.getProduitsIds().contains(savedProduit.getIdProduit())) {
                    additif.getProduitsIds().add(savedProduit.getIdProduit());
                    additifRepository.save(additif);
                }
            });

        }
        return savedProduit;
    }
}
