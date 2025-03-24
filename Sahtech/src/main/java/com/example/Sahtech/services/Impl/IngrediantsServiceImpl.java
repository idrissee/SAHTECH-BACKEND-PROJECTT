package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.repositories.IngrediantsRepository;
import com.example.Sahtech.services.IngrediantsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IngrediantsServiceImpl implements IngrediantsService {

    IngrediantsRepository ingrediantsRepository;


    public IngrediantsServiceImpl(IngrediantsRepository ingrediantsRepository) {
        this.ingrediantsRepository = ingrediantsRepository;
    }

    @Override
    public Ingrediants save(Ingrediants ingrediants) {
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

    @Override
    public Optional<Ingrediants> findOnebyId(Long id) {
        return ingrediantsRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return ingrediantsRepository.existsById(id);
    }

    @Override
    public Ingrediants partialUpdate(Long id,Ingrediants ingredient) {
        ingredient.setIdIngrediant(id);

        return ingrediantsRepository.findById(id).map(exisitingIngredient ->{
            Optional.ofNullable(ingredient.getNomIngrediant()).ifPresent(exisitingIngredient::setNomIngrediant);
            Optional.ofNullable(ingredient.getQuantite()).ifPresent(exisitingIngredient::setQuantite);
            return ingrediantsRepository.save(exisitingIngredient);
        }).orElseThrow(() -> new RuntimeException("Ingrediants not found"));
    }
}


