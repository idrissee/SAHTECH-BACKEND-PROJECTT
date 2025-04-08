package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.NutriScoreDto;
import com.example.Sahtech.entities.NutriScore;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NutriScoreMapperImpl implements Mapper<NutriScore, NutriScoreDto> {

    private final ModelMapper modelMapper;

    public NutriScoreMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public NutriScoreDto mapTo(NutriScore nutriScore) {
        return modelMapper.map(nutriScore, NutriScoreDto.class);
    }

    @Override
    public NutriScore mapFrom(NutriScoreDto nutriScoreDto) {
        return modelMapper.map(nutriScoreDto, NutriScore.class);
    }
} 