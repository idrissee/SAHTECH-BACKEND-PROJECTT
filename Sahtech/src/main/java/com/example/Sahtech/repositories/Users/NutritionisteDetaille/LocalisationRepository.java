package com.example.Sahtech.repositories.Users.NutritionisteDetaille;

import com.example.Sahtech.entities.Users.NutritionisteDetaille.Localisation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalisationRepository extends MongoRepository<Localisation, String> {
    
    // Trouver les localisations par région
    List<Localisation> findByRegion(String region);
    
    // Trouver les localisations par ville
    List<Localisation> findByVille(String ville);
    
    // Trouver les localisations par code postal
    List<Localisation> findByCodePostal(String codePostal);
    
    // Trouver les localisations par pays
    List<Localisation> findByPays(String pays);
} 