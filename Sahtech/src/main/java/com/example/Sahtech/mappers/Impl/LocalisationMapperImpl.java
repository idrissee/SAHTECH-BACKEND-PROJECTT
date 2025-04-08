package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.LocalisationDto;
import com.example.Sahtech.entities.Localisation;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LocalisationMapperImpl implements Mapper<Localisation, LocalisationDto> {
    
    private final ModelMapper modelMapper;
    
    public LocalisationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    @Override
    public LocalisationDto mapTo(Localisation localisation) {
        return modelMapper.map(localisation, LocalisationDto.class);
    }
    
    @Override
    public Localisation mapFrom(LocalisationDto localisationDto) {
        return modelMapper.map(localisationDto, Localisation.class);
    }
} 