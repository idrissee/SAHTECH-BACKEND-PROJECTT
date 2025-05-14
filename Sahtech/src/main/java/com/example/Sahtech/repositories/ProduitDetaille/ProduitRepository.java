package com.example.Sahtech.repositories.ProduitDetaille;


import com.example.Sahtech.entities.ProduitDetaille.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {
    Optional<Produit> findByCodeBarre(String codeBarre);
    
    // Add a custom query method to find by codeBarre as Long
    @Query(value = "{'codeBarre': ?0}")
    Optional<Produit> findByCodeBarreNumeric(Long codeBarre);
}
