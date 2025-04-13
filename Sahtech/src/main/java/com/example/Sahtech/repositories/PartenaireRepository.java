package com.example.Sahtech.repositories;

import com.example.Sahtech.Enum.StatutPartenaire;
import com.example.Sahtech.entities.Partenaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Partenaire
 */
@Repository
public interface PartenaireRepository extends MongoRepository<Partenaire, String> {
    
    /**
     * Trouver un partenaire par son email
     */
    Optional<Partenaire> findByEmail(String email);
    
    /**
     * Trouver les partenaires par leur domaine d'activité
     */
    List<Partenaire> findByDomaineActivite(String domaineActivite);
    
    /**
     * Trouver les partenaires par leur statut
     */
    List<Partenaire> findByStatut(StatutPartenaire statut);
    
    /**
     * Trouver les partenaires dont le nom contient la chaîne spécifiée (insensible à la casse)
     */
    List<Partenaire> findByNomContainingIgnoreCase(String nom);
} 