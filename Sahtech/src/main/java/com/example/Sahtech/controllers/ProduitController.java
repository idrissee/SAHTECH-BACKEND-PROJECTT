package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.ProduitDto;
import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.mappers.Mapper;
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
    private Mapper<Produit, ProduitDto> produitMapper;

    public ProduitController(ProduitService produitService, 
                            Mapper<Produit, ProduitDto> produitMapper) {
        this.produitService = produitService;
        this.produitMapper = produitMapper;
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
    public ResponseEntity<ProduitDto> getProduit(@PathVariable("id") Long id){
        Optional<Produit> foundproduit = produitService.findOnebyId(id);
        return foundproduit.map(produit-> {
            ProduitDto produitDto = produitMapper.mapTo(produit);
            return new ResponseEntity<>(produitDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "produits/{id}")
    public ResponseEntity<ProduitDto> fullUpdateProduit(
            @PathVariable("id") Long id,
            @RequestBody ProduitDto produitDto) {

        if(!produitService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        produitDto.setId(id);
        Produit produit = produitMapper.mapFrom(produitDto);
        Produit savedProduit = produitService.save(produit);
        return new ResponseEntity<>(
                produitMapper.mapTo(savedProduit)
                , HttpStatus.OK);
    }

    @PatchMapping(path ="produits/{id}")
    public ResponseEntity<ProduitDto> partialUpdateProduit(
            @PathVariable("id") Long id,
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
    public ResponseEntity deleteProduit(@PathVariable("id") Long id){
        produitService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping(path = "produits/{id}/nutriscore")
    public ResponseEntity<ValeurNutriScore> getNutriScoreForProduit(@PathVariable("id") Long id) {
        // Récupérer le produit
        Optional<Produit> foundProduit = produitService.findOnebyId(id);
        
        if (foundProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Produit produit = foundProduit.get();
        
        // Vérifier si le produit a un NutriScore
        if (produit.getValeurNutriScore() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Retourner directement la valeur du NutriScore
        return new ResponseEntity<>(produit.getValeurNutriScore(), HttpStatus.OK);
    }
    
    @PutMapping(path = "produits/{id}/nutriscore/{valeurNutriScore}")
    public ResponseEntity<ProduitDto> setNutriScoreForProduit(
            @PathVariable("id") Long id,
            @PathVariable("valeurNutriScore") ValeurNutriScore valeurNutriScore) {
        
        // Vérifier que le produit existe
        if (!produitService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Récupérer le produit
        Optional<Produit> foundProduit = produitService.findOnebyId(id);
        
        if (foundProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Produit produit = foundProduit.get();
        
        // Mettre à jour le NutriScore du produit
        produit.setValeurNutriScore(valeurNutriScore);
        
        // Mettre à jour le score numérique en fonction de la valeur
        switch (valeurNutriScore) {
            case A:
                produit.setScoreNumerique(1);
                break;
            case B:
                produit.setScoreNumerique(2);
                break;
            case C:
                produit.setScoreNumerique(3);
                break;
            case D:
                produit.setScoreNumerique(4);
                break;
            case E:
                produit.setScoreNumerique(5);
                break;
        }
        
        Produit updatedProduit = produitService.save(produit);
        
        return new ResponseEntity<>(produitMapper.mapTo(updatedProduit), HttpStatus.OK);
    }
}
