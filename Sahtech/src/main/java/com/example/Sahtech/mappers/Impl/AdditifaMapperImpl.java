package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.ProduitDetaille.AdditifsDto;
import com.example.Sahtech.entities.ProduitDetaille.Additifs;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
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
