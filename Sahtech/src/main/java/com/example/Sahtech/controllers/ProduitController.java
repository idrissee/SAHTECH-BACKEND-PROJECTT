package com.example.Sahtech.controllers;


import com.example.Sahtech.Dto.ProduitDto;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.ProduitService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProduitController {

    private ProduitService produitService;

    private Mapper<Produit, ProduitDto> produitMapper;

    public ProduitController( ProduitService produitService, Mapper<Produit, ProduitDto> produitMapper) {
        this.produitService = produitService;
        this.produitMapper = produitMapper;
    }

    @PostMapping(path = "/produit")
    public ProduitDto createProduit(@RequestBody ProduitDto produitDto) {
        Produit produit = produitMapper.mapFrom(produitDto);
        Produit savedProduit = produitService.createProduit(produit);
        return produitMapper.mapTo(savedProduit);
    }

}
