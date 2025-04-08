package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.UtilisateursDto;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapperImpl implements Mapper<Utilisateurs, UtilisateursDto> {

    private final ModelMapper modelMapper;

    public UtilisateurMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UtilisateursDto mapTo(Utilisateurs utilisateur) {
        return modelMapper.map(utilisateur, UtilisateursDto.class);
    }

    @Override
    public Utilisateurs mapFrom(UtilisateursDto utilisateurDto) {
        return modelMapper.map(utilisateurDto, Utilisateurs.class);
    }
} 