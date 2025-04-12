package com.example.Sahtech.repositories;

import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.NutriScore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutriScoreRepository extends MongoRepository<NutriScore, String> {
    
    // Trouver tous les NutriScores ayant une certaine valeur
    List<NutriScore> findByValeur(ValeurNutriScore valeur);
    
    // Trouver par valeur (A, B, C, D, E)
    Optional<NutriScore> findByValeur(ValeurNutriScore valeur);
    
    // Trouver par score numérique
    Optional<NutriScore> findByScoreNumerique(Integer scoreNumerique);
} 