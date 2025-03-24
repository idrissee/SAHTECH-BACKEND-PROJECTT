package com.example.Sahtech.services;

import com.example.Sahtech.entities.Ingrediants;

import java.util.List;

public interface IngrediantsService {

    Ingrediants createIngrediants(Ingrediants ingrediants);

    List<Ingrediants> findAll();
}
