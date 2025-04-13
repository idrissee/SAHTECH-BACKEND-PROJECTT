package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Localisation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalisationRepository extends MongoRepository<Localisation, String> {
    
    // Trouver par ville
    List<Localisation> findByVille(String ville);
    
    // Trouver par pays
    List<Localisation> findByPays(String pays);
    
    // Trouver par région
    List<Localisation> findByRegion(String region);

    // Trouver par code postal
    Optional<Localisation> findByCodePostal(String codePostal);

    // Trouver par coordonnées proches (approximatives)
    List<Localisation> findByLatitudeBetweenAndLongitudeBetween(Double latMin, Double latMax, Double longMin, Double longMax);

}