package com.example.Sahtech.services;

import com.example.Sahtech.entities.Localisation;

import java.util.List;
import java.util.Optional;

public interface LocalisationService {
    
    // Trouver toutes les localisations
    List<Localisation> findAll();
    
    // Trouver une localisation par son ID
    Optional<Localisation> findOneById(String id);
    
    // Vérifier si une localisation existe
    boolean isExists(String id);
    
    // Sauvegarder une localisation
    Localisation save(Localisation localisation);
    
    // Mettre à jour une localisation
    Localisation update(String id, Localisation localisation);
    
    // Supprimer une localisation
    void delete(String id);
    
    // Trouver des localisations par pays
    List<Localisation> findByPays(String pays);
    
    // Trouver des localisations par région
    List<Localisation> findByRegion(String region);
    
    // Trouver des localisations par ville
    List<Localisation> findByVille(String ville);

    // Trouver des localisations par code postal
    List<Localisation> findByCodePostal(String codePostal);

    // Trouver des localisations par pays
    List<Localisation> findAllByCountry(String country);
    
    // Trouver des localisations par région
    List<Localisation> findAllByRegion(String region);
    
    // Trouver des localisations par ville
    List<Localisation> findAllByCity(String city);
    
    // Trouver des localisations par code postal
    List<Localisation> findAllByPostalCode(String postalCode);
} 