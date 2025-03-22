package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.repositories.IngrediantsRepository;
import com.example.Sahtech.services.IngrediantsService;
import org.springframework.stereotype.Service;

@Service
public class IngrediantsServiceImpl implements IngrediantsService {

    IngrediantsRepository ingrediantsRepository;


    public IngrediantsServiceImpl(IngrediantsRepository ingrediantsRepository) {
        this.ingrediantsRepository = ingrediantsRepository;
    }

    @Override
    public Ingrediants createIngrediants(Ingrediants ingrediants) {
        return ingrediantsRepository.save(ingrediants);
    }
}
