package com.example.Sahtech.services.Impl;


import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.repositories.AdditifsRepository;
import com.example.Sahtech.repositories.ProduitRepository;
import com.example.Sahtech.services.ProduitService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
                if (!additif.getProduitsIds().contains(savedProduit.getId())) {
                    additif.getProduitsIds().add(savedProduit.getId());
                    additifRepository.save(additif);
                }
            });

        }
        return savedProduit;
    }

    @Override
    public List<Produit> findAll() {
        return StreamSupport.stream(produitRepository
                .findAll()
                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Produit> findOnebyId(Long id) {
        return produitRepository.findById(id);
    }

    @Override
    public Optional<Produit> findByCodeBarre(String codeBarre) {
        return produitRepository.findByCodeBarre(codeBarre);
    }

    @Override
    public boolean isExists(Long id) {
        return produitRepository.existsById(id);
    }

    @Override
    public Produit partialUpdate(Long id, Produit produit) {

        produit.setId(id);

        return produitRepository.findById(id).map(exisitingProduit ->{
            Optional.ofNullable(produit.getNom()).ifPresent(exisitingProduit::setNom);
            Optional.ofNullable(produit.getDescription()).ifPresent(exisitingProduit::setDescription);
            return produitRepository.save(exisitingProduit);
        }).orElseThrow(() -> new RuntimeException("Produit not found"));


    }

    @Override
    public void delete(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit save(Produit produit) {
       return  produitRepository.save(produit);
    }
}
