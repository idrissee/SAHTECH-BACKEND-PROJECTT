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
        UtilisateursDto dto = modelMapper.map(utilisateur, UtilisateursDto.class);
        // Explicitly set photoUrl to ensure it's included in the response
        dto.setPhotoUrl(utilisateur.getPhotoUrl());
        return dto;
    }

    @Override
    public Utilisateurs mapFrom(UtilisateursDto utilisateurDto) {
        Utilisateurs utilisateur = modelMapper.map(utilisateurDto, Utilisateurs.class);
        // Ensure photoUrl is set when mapping from DTO to entity
        utilisateur.setPhotoUrl(utilisateurDto.getPhotoUrl());
        return utilisateur;
    }
} 