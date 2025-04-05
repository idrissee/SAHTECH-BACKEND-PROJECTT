package com.example.Sahtech.services;

import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.NutriScore;

import java.util.List;
import java.util.Optional;

public interface NutriScoreService {

    
    // Trouver tous les Nutri-Scores
    List<NutriScore> findAll();
    
    // Trouver un Nutri-Score par son ID
    Optional<NutriScore> findOneById(Long id);
    
    // Vérifier si un Nutri-Score existe
    boolean isExists(Long id);
    
    // Sauvegarder un Nutri-Score
    NutriScore save(NutriScore nutriScore);
    
    // Mettre à jour un Nutri-Score
    NutriScore update(Long id, NutriScore nutriScore);
    
    // Supprimer un Nutri-Score
    void delete(Long id);
    
    // Trouver tous les Nutri-Scores d'une certaine valeur
    List<NutriScore> findByValeur(ValeurNutriScore valeur);
} 