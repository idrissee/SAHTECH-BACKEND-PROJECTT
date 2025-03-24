package com.example.Sahtech.Controllers;


import com.example.Sahtech.Dto.IngrediantsDto;
import com.example.Sahtech.Dto.ProduitDto;
import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.IngrediantsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class IngrediantsController {

    private IngrediantsService ingrediantsService;

    private Mapper<Ingrediants, IngrediantsDto> ingrediantsMapper;


    public IngrediantsController(IngrediantsService ingrediantsService,Mapper<Ingrediants,IngrediantsDto> ingrediantsMapper) {
        this.ingrediantsService = ingrediantsService;
        this.ingrediantsMapper = ingrediantsMapper;
    }

    @PostMapping(path = "ingrediants")
    public ResponseEntity<IngrediantsDto> createIngrediants(@RequestBody IngrediantsDto ingrediantsDto) {
        Ingrediants ingrediants = ingrediantsMapper.mapFrom(ingrediantsDto);
        Ingrediants ingrediantsSaved = ingrediantsService.createIngrediants(ingrediants);
        return new ResponseEntity<>(ingrediantsMapper.mapTo(ingrediantsSaved), HttpStatus.CREATED);
    }

    @GetMapping(path ="/ingrediants")
    public List<IngrediantsDto> listIngrediants() {
        List<Ingrediants> ingrediants  =ingrediantsService.findAll();
        return  ingrediants.stream()
                .map(ingrediantsMapper::mapTo)
                .collect(Collectors.toList());
    }
}
