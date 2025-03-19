package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.ProduitDto;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProduitMapperImpl implements Mapper<Produit, ProduitDto> {

    private final ModelMapper modelMapper;

    public ProduitMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProduitDto mapTo(Produit produit) {
        return modelMapper.map(produit, ProduitDto.class);
    }

    @Override
    public Produit mapFrom(ProduitDto produitDto) {
       return modelMapper.map(produitDto, Produit.class);
    }
}
