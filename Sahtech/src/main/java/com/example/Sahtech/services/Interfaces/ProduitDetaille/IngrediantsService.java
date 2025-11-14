package com.example.Sahtech.services.Interfaces.ProduitDetaille;

import com.example.Sahtech.entities.ProduitDetaille.Ingrediants;

import java.util.List;
import java.util.Optional;

public interface IngrediantsService {

    Ingrediants save(Ingrediants ingrediants);

    List<Ingrediants> findAll();

    Optional<Ingrediants> findOnebyId(String id);

    boolean isExists(String id);

    Ingrediants partialUpdate(String id ,Ingrediants ingredient);

    void delete(String id);

    Ingrediants getByNomIngrediant(String nom);
}
