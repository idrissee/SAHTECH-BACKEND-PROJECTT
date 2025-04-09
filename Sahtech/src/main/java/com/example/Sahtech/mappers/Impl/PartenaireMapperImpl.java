package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.PartenaireDto;
import com.example.Sahtech.entities.Partenaire;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PartenaireMapperImpl implements Mapper<Partenaire, PartenaireDto> {

    private final ModelMapper modelMapper;

    public PartenaireMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PartenaireDto mapTo(Partenaire partenaire) {
        return modelMapper.map(partenaire, PartenaireDto.class);
    }

    @Override
    public Partenaire mapFrom(PartenaireDto partenaireDto) {
        return modelMapper.map(partenaireDto, Partenaire.class);
    }
} 