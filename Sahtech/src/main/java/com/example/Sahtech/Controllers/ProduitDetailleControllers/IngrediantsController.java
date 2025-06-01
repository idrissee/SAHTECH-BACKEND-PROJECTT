package com.example.Sahtech.Controllers.ProduitDetailleControllers;

import com.example.Sahtech.Dto.ProduitDetaille.IngrediantsDto;
import com.example.Sahtech.entities.ProduitDetaille.Ingrediants;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.IngrediantsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Ingredients")
public class IngrediantsController {

    private IngrediantsService ingrediantsService;

    private Mapper<Ingrediants, IngrediantsDto> ingrediantsMapper;


    public IngrediantsController(IngrediantsService ingrediantsService,Mapper<Ingrediants,IngrediantsDto> ingrediantsMapper) {
        this.ingrediantsService = ingrediantsService;
        this.ingrediantsMapper = ingrediantsMapper;
    }

    @PostMapping()
    public ResponseEntity<IngrediantsDto> save(@RequestBody IngrediantsDto ingrediantsDto) {
        Ingrediants ingrediants = ingrediantsMapper.mapFrom(ingrediantsDto);
        Ingrediants ingrediantsSaved = ingrediantsService.save(ingrediants);
        return new ResponseEntity<>(ingrediantsMapper.mapTo(ingrediantsSaved), HttpStatus.CREATED);
    }

    @GetMapping(path ="/All")
    public List<IngrediantsDto> listIngrediants() {
        List<Ingrediants> ingrediants  =ingrediantsService.findAll();
        return  ingrediants.stream()
                .map(ingrediantsMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngrediantsDto> getIngrediant(@PathVariable("id") String id){
        Optional<Ingrediants> foundingrediant = ingrediantsService.findOnebyId(id);
        return foundingrediant.map(ingrediant-> {
           IngrediantsDto ingrediantsDto = ingrediantsMapper.mapTo(ingrediant);
            return new ResponseEntity<>(ingrediantsDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<IngrediantsDto> fullUpdateIngredient(
            @PathVariable("id") String id,
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

    @PatchMapping(path ="/{id}")
    public ResponseEntity<IngrediantsDto> partialUpdateIngredient(
            @PathVariable("id") String id,
                @RequestBody IngrediantsDto ingredientDto){

        if(!ingrediantsService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Ingrediants ingredient = ingrediantsMapper.mapFrom(ingredientDto);
        Ingrediants savedIngredient = ingrediantsService.partialUpdate(id,ingredient);
        return new ResponseEntity<>(
                ingrediantsMapper.mapTo(savedIngredient)
                , HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable("id") String id) {
        ingrediantsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/nomIngredient")
    public ResponseEntity<IngrediantsDto> findIngredientByNom(@RequestParam("nomIngredient") String nom){
        Ingrediants ingrediants=ingrediantsService.getByNomIngrediant(nom);
        if (!(ingrediants==null)) {
            return new ResponseEntity<>(ingrediantsMapper.mapTo(ingrediants), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
