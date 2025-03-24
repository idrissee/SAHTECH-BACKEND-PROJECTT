package com.example.Sahtech.services;

import com.example.Sahtech.entities.Ingrediants;

import java.util.List;
import java.util.Optional;

public interface IngrediantsService {

    Ingrediants createIngrediants(Ingrediants ingrediants);

    List<Ingrediants> findAll();

    Optional<Ingrediants> findOnebyId(Long id);
}
