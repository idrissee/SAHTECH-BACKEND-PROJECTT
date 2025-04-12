package com.example.Sahtech.Controllers;


import com.example.Sahtech.Dto.NutriScoreDto;
import com.example.Sahtech.Dto.ProduitDto;
import com.example.Sahtech.entities.NutriScore;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.NutriScoreService;
import com.example.Sahtech.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class ProduitController {

    private ProduitService produitService;
    private NutriScoreService nutriScoreService;
    private Mapper<Produit, ProduitDto> produitMapper;
    private Mapper<NutriScore, NutriScoreDto> nutriScoreMapper;

    public ProduitController(ProduitService produitService, NutriScoreService nutriScoreService, 
                            Mapper<Produit, ProduitDto> produitMapper, Mapper<NutriScore, NutriScoreDto> nutriScoreMapper) {
        this.produitService = produitService;
        this.nutriScoreService = nutriScoreService;
        this.produitMapper = produitMapper;
        this.nutriScoreMapper = nutriScoreMapper;
    }

    @PostMapping(path = "/produits")
    public ResponseEntity<ProduitDto> createProduit(@RequestBody ProduitDto produitDto) {
        Produit produit = produitMapper.mapFrom(produitDto);
        Produit savedProduit = produitService.createProduit(produit);
        return new ResponseEntity<>(produitMapper.mapTo(savedProduit), HttpStatus.CREATED);
    }


    @GetMapping(path ="/produits")
    public List<ProduitDto> listProduits() {
            List<Produit> produits  = produitService.findAll();
           return  produits.stream()
                   .map(produitMapper::mapTo)
                   .collect(Collectors.toList());
    }

    @GetMapping(path ="produits/{id}")
    public ResponseEntity<ProduitDto> getProduit(@PathVariable("id") String id){
        Optional<Produit> foundproduit = produitService.findOnebyId(id);
        return foundproduit.map(produit-> {
            ProduitDto produitDto = produitMapper.mapTo(produit);
            return new ResponseEntity<>(produitDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "produits/{id}")
    public ResponseEntity<ProduitDto> fullUpdateProduit(
            @PathVariable("id") String id,
            @RequestBody ProduitDto produitDto) {

        if(!produitService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        produitDto.setIdProduit(id);
        Produit produit = produitMapper.mapFrom(produitDto);
        Produit savedProduit = produitService.save(produit);
        return new ResponseEntity<>(
                produitMapper.mapTo(savedProduit)
                , HttpStatus.OK);
    }

    @PatchMapping(path ="produits/{id}")
    public ResponseEntity<ProduitDto> partialUpdateProduit(
            @PathVariable("id") String id,
            @RequestBody ProduitDto produitDto){

        if(!produitService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Produit produit = produitMapper.mapFrom(produitDto);
        Produit updatedProduit = produitService.partialUpdate(id,produit);
        return new ResponseEntity<>(
                produitMapper.mapTo(updatedProduit),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "produits/{id}")
    public ResponseEntity deleteProduit(@PathVariable("id") String id){
        produitService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping(path = "produits/{id}/nutriscore")
    public ResponseEntity<NutriScoreDto> getNutriScoreForProduit(@PathVariable("id") String id) {
        // Récupérer d'abord le produit
        Optional<Produit> foundProduit = produitService.findOnebyId(id);
        
        if (foundProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Produit produit = foundProduit.get();
        
        // Vérifier si le produit a un NutriScore
        if (produit.getNutriScoreId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Récupérer le NutriScore
        Optional<NutriScore> foundNutriScore = nutriScoreService.findOneById(produit.getNutriScoreId());
        
        return foundNutriScore.map(nutriScore -> {
            NutriScoreDto nutriScoreDto = nutriScoreMapper.mapTo(nutriScore);
            return new ResponseEntity<>(nutriScoreDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "produits/{id}/nutriscore/{nutriScoreId}")
    public ResponseEntity<ProduitDto> setNutriScoreForProduit(
            @PathVariable("id") String id,
            @PathVariable("nutriScoreId") String nutriScoreId) {
        
        // Vérifier que le produit existe
        if (!produitService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Vérifier que le NutriScore existe
        if (!nutriScoreService.isExists(nutriScoreId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Récupérer le produit
        Optional<Produit> foundProduit = produitService.findOnebyId(id);
        
        if (foundProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Produit produit = foundProduit.get();
        
        // Mettre à jour le NutriScore du produit
        produit.setNutriScoreId(nutriScoreId);
        Produit updatedProduit = produitService.save(produit);
        
        return new ResponseEntity<>(produitMapper.mapTo(updatedProduit), HttpStatus.OK);
    }
}
