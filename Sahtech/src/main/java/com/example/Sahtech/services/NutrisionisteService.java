package com.example.Sahtech.services;

import com.example.Sahtech.entities.Nutrisioniste;

import java.util.List;
import java.util.Optional;

public interface NutrisionisteService {

    
    // Trouver tous les nutritionnistes
    List<Nutrisioniste> findAll();
    
    // Trouver un nutritionniste par son ID
    Optional<Nutrisioniste> findOneById(Long id);
    
    // Vérifier si un nutritionniste existe
    boolean isExists(Long id);
    
    // Sauvegarder un nutritionniste
    Nutrisioniste save(Nutrisioniste nutrisioniste);
    
    // Mettre à jour un nutritionniste
    Nutrisioniste update(Long id, Nutrisioniste nutrisioniste);
    
    // Supprimer un nutritionniste
    void delete(Long id);
    
    // Trouver les nutritionnistes par localisation
    List<Nutrisioniste> findByLocalisationId(Long localisationId);
    
    // Trouver les nutritionnistes par spécialité
    List<Nutrisioniste> findBySpecialite(String specialite);
    
    // Trouver les nutritionnistes vérifiés
    List<Nutrisioniste> findByEstVerifie(Boolean estVerifie);
} 