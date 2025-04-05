package com.example.Sahtech.repositories;

import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.NutriScore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutriScoreRepository extends MongoRepository<NutriScore, Long> {
    
    // Trouver tous les NutriScores ayant une certaine valeur
    List<NutriScore> findByValeur(ValeurNutriScore valeur);
} 