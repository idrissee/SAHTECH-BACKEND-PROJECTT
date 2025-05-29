package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.Users.UtilisateursDto;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UtilisateurMapperImpl implements Mapper<Utilisateurs, UtilisateursDto> {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurMapperImpl.class);
    private final ModelMapper modelMapper;

    public UtilisateurMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UtilisateursDto mapTo(Utilisateurs utilisateur) {
        logger.debug("Conversion de l'entité Utilisateurs vers DTO - ID: {}", utilisateur.getId());
        logger.debug("Valeurs de l'entité - Poids: {}, Taille: {}, IMC: {}", 
                   utilisateur.getPoids(), utilisateur.getTaille(), utilisateur.getImc());
        
        UtilisateursDto dto = modelMapper.map(utilisateur, UtilisateursDto.class);
        
        // Explicitly set photoUrl to ensure it's included in the response
        dto.setPhotoUrl(utilisateur.getPhotoUrl());
        // Explicitly set IMC to ensure it's included in the response
        dto.setImc(utilisateur.getImc());
        
        logger.debug("Valeurs du DTO après conversion - Poids: {}, Taille: {}, IMC: {}", 
                   dto.getPoids(), dto.getTaille(), dto.getImc());
        
        return dto;
    }

    @Override
    public Utilisateurs mapFrom(UtilisateursDto utilisateurDto) {
        logger.debug("Conversion du DTO vers l'entité Utilisateurs");
        logger.debug("Valeurs du DTO - Poids: {}, Taille: {}, IMC: {}", 
                   utilisateurDto.getPoids(), utilisateurDto.getTaille(), utilisateurDto.getImc());
        
        Utilisateurs utilisateur = modelMapper.map(utilisateurDto, Utilisateurs.class);
        
        // Ensure photoUrl is set when mapping from DTO to entity
        utilisateur.setPhotoUrl(utilisateurDto.getPhotoUrl());
        // Ensure IMC is set when mapping from DTO to entity (if provided)
        utilisateur.setImc(utilisateurDto.getImc());
        
        // Mettre à jour l'interprétation de l'IMC si l'IMC est défini
        if (utilisateur.getImc() != null) {
            utilisateur.updateInterpretationIMC();
        }
        
        logger.debug("Valeurs de l'entité après conversion - Poids: {}, Taille: {}, IMC: {}", 
                   utilisateur.getPoids(), utilisateur.getTaille(), utilisateur.getImc());
        
        return utilisateur;
    }
} 