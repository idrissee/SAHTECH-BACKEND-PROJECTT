package com.example.Sahtech.Controllers;


import com.example.Sahtech.Dto.ProduitDto;
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

    public ProduitController( ProduitService produitService, Mapper<Produit, ProduitDto> produitMapper) {
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

        produitDto.setIdProduit(id);
        Produit produit = produitMapper.mapFrom(produitDto);
        Produit savedProduit = produitService.save(produit);
        return new ResponseEntity<>(
                produitMapper.mapTo(savedProduit)
                , HttpStatus.OK);
    }

}
