package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.Users.NutritionisteDetaille.NutrisionisteDto;
import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NutrisoinisteMapperImpl implements Mapper<Nutrisioniste, NutrisionisteDto> {

    private final ModelMapper modelMapper;

    public NutrisoinisteMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public NutrisionisteDto mapTo(Nutrisioniste nutrisioniste) {
        return modelMapper.map(nutrisioniste, NutrisionisteDto.class);
    }

    @Override
    public Nutrisioniste mapFrom(NutrisionisteDto nutrisionisteDto) {
        return modelMapper.map(nutrisionisteDto, Nutrisioniste.class);
    }
} 