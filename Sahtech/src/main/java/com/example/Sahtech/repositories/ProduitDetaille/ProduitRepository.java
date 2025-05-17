package com.example.Sahtech.repositories.ProduitDetaille;


import com.example.Sahtech.entities.ProduitDetaille.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {
    Optional<Produit> findByCodeBarre(Long codeBarre);
}
