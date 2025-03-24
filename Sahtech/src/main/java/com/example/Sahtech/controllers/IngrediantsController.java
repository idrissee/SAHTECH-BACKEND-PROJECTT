package com.example.Sahtech.Controllers;


import com.example.Sahtech.Dto.IngrediantsDto;
import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.IngrediantsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<IngrediantsDto> save(@RequestBody IngrediantsDto ingrediantsDto) {
        Ingrediants ingrediants = ingrediantsMapper.mapFrom(ingrediantsDto);
        Ingrediants ingrediantsSaved = ingrediantsService.save(ingrediants);
        return new ResponseEntity<>(ingrediantsMapper.mapTo(ingrediantsSaved), HttpStatus.CREATED);
    }

    @GetMapping(path ="/ingrediants")
    public List<IngrediantsDto> listIngrediants() {
        List<Ingrediants> ingrediants  =ingrediantsService.findAll();
        return  ingrediants.stream()
                .map(ingrediantsMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path ="ingrediants/{id}")
    public ResponseEntity<IngrediantsDto> getIngrediant(@PathVariable("id") Long id){
        Optional<Ingrediants> foundingrediant = ingrediantsService.findOnebyId(id);
        return foundingrediant.map(produit-> {
           IngrediantsDto ingrediantsDto = ingrediantsMapper.mapTo(produit);
            return new ResponseEntity<>(ingrediantsDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "ingrediants/{id}")
    public ResponseEntity<IngrediantsDto> fullUpdateIngredient(
            @PathVariable("id") Long id,
            @RequestBody IngrediantsDto ingrediantsDto) {

        if(!ingrediantsService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ingrediantsDto.setIdIngrediant(id);
        Ingrediants ingredient = ingrediantsMapper.mapFrom(ingrediantsDto);
        Ingrediants savedIngredient = ingrediantsService.save(ingredient);
        return new ResponseEntity<>(
                ingrediantsMapper.mapTo(savedIngredient)
                , HttpStatus.OK);
    }
}
