package com.example.Sahtech.repositories.ProduitDetaille;

import com.example.Sahtech.entities.ProduitDetaille.Ingrediants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngrediantsRepository extends MongoRepository<Ingrediants, String> {
    List<Ingrediants> findByProduitId(String produitId);
    Ingrediants findByNomIngrediant(String nomIngrediant);
}
