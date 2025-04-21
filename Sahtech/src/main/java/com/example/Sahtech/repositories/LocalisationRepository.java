package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Localisation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalisationRepository extends MongoRepository<Localisation, String> {
    
    // Trouver les localisations par région
    List<Localisation> findByRegion(String region);
    
    // Trouver les localisations par ville
    List<Localisation> findByVille(String ville);
    
    // Trouver les localisations par code postal
    List<Localisation> findByCodePostal(String codePostal);
} 