package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.PubliciteDto;
import com.example.Sahtech.entities.Publicite;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PubliciteMapperImpl implements Mapper<Publicite, PubliciteDto> {

    private final ModelMapper modelMapper;

    public PubliciteMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PubliciteDto mapTo(Publicite publicite) {
        return modelMapper.map(publicite, PubliciteDto.class);
    }

    @Override
    public Publicite mapFrom(PubliciteDto publiciteDto) {
       return modelMapper.map(publiciteDto, Publicite.class);
    }
} 