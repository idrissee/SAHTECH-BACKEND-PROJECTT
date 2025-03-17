package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.AdditifsDto;
import com.example.Sahtech.Dto.IngrediantsDto;
import com.example.Sahtech.entities.Additifs;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;

public class AdditifaMapperImpl implements Mapper<Additifs, AdditifsDto> {
    private final ModelMapper modelMapper ;

    public AdditifaMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public AdditifsDto mapTo(Additifs additifs) {
        return modelMapper.map(additifs, AdditifsDto.class);
    }

    @Override
    public Additifs mapFrom(AdditifsDto additifsDto) {
        return modelMapper.map(additifsDto, Additifs.class);
    }
}
