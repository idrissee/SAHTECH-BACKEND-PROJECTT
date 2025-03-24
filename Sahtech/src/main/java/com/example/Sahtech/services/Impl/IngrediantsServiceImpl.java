package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.repositories.IngrediantsRepository;
import com.example.Sahtech.services.IngrediantsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<Ingrediants> findAll() {
        return StreamSupport.stream(ingrediantsRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
}


