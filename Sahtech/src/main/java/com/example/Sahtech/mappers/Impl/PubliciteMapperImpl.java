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
        PubliciteDto publiciteDto = modelMapper.map(publicite, PubliciteDto.class);
        if (publicite.getPartenaire() != null) {
            publiciteDto.setPartenaireId(publicite.getPartenaire().getId());
        }
        return publiciteDto;
    }

    @Override
    public Publicite mapFrom(PubliciteDto publiciteDto) {
        Publicite publicite = modelMapper.map(publiciteDto, Publicite.class);
        publicite.setPartenaire_id(publiciteDto.getPartenaireId());
        return publicite;
    }
} 