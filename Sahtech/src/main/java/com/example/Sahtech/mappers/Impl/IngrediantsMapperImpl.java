package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.ProduitDetaille.IngrediantsDto;
import com.example.Sahtech.entities.ProduitDetaille.Ingrediants;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class IngrediantsMapperImpl implements Mapper<Ingrediants, IngrediantsDto> {

    private final ModelMapper modelMapper ;

    public IngrediantsMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public IngrediantsDto mapTo(Ingrediants ingrediants) {
        return modelMapper.map(ingrediants, IngrediantsDto.class) ;
    }

    @Override
    public Ingrediants mapFrom(IngrediantsDto ingrediantsDto) {
        return modelMapper.map(ingrediantsDto, Ingrediants.class) ;
    }
}
