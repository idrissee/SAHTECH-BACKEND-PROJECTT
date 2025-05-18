package com.example.Sahtech.repositories.ProduitDetaille;


import com.example.Sahtech.entities.ProduitDetaille.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {
    Optional<Produit> findByCodeBarre(Long codeBarre);
    
    @Query(value = "{'codeBarre': ?0}", fields = "{'id': 1, 'nom': 1, 'codeBarre': 1, 'imageUrl': 1}")
    Optional<Produit> findByCodeBarreExact(Long codeBarre);
}
