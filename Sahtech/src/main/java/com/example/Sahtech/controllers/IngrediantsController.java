package com.example.Sahtech.controllers;


import com.example.Sahtech.Dto.IngrediantsDto;
import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.IngrediantsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngrediantsController {

    private IngrediantsService ingrediantsService;

    private Mapper<Ingrediants, IngrediantsDto> ingrediantsMapper;


    public IngrediantsController(IngrediantsService ingrediantsService,Mapper<Ingrediants,IngrediantsDto> ingrediantsMapper) {
        this.ingrediantsService = ingrediantsService;
        this.ingrediantsMapper = ingrediantsMapper;
    }

    @PostMapping(path = "ingrediants")
    public IngrediantsDto createIngrediants(@RequestBody IngrediantsDto ingrediantsDto) {
        Ingrediants ingrediants = ingrediantsMapper.mapFrom(ingrediantsDto);
        Ingrediants ingrediantsSaved = ingrediantsService.createIngrediants(ingrediants);
        return ingrediantsMapper.mapTo(ingrediantsSaved);
    }
}
