package com.example.Sahtech.services.Impl.ProduitDetaille;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.repositories.ProduitDetaille.AdditifsRepository;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.ProduitService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProduitServiceImpl implements ProduitService {

    ProduitRepository produitRepository;
    private final AdditifsRepository additifRepository;


    public ProduitServiceImpl(ProduitRepository produitRepository, 
                             AdditifsRepository additifRepository) {
        this.produitRepository = produitRepository;
        this.additifRepository = additifRepository;
    }

    @Override
    public Produit createProduit(Produit produit) {
        Produit savedProduit = produitRepository.save(produit);
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
    public Optional<Produit> findOnebyId(String id) {
        return produitRepository.findById(id);
    }

    @Override
    public Optional<Produit> findByCodeBarre(String codeBarre) {
        return produitRepository.findByCodeBarre(codeBarre);
    }

    @Override
    public boolean isExists(String id) {
        return produitRepository.existsById(id);
    }

    @Override
    public Produit partialUpdate(String id, Produit produit) {

        produit.setId(id);

        return produitRepository.findById(id).map(exisitingProduit ->{
            Optional.ofNullable(produit.getNom()).ifPresent(exisitingProduit::setNom);
            Optional.ofNullable(produit.getDescription()).ifPresent(exisitingProduit::setDescription);
            return produitRepository.save(exisitingProduit);
        }).orElseThrow(() -> new RuntimeException("Produit not found"));
    }

    @Override
    public void delete(String id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit save(Produit produit) {
       return  produitRepository.save(produit);
    }

}
