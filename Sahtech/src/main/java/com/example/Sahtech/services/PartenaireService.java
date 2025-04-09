package com.example.Sahtech.services;

import com.example.Sahtech.Enum.StatutPartenaire;
import com.example.Sahtech.entities.Partenaire;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des partenaires
 */
public interface PartenaireService {
    
    /**
     * Créer un nouveau partenaire
     */
    Partenaire save(Partenaire partenaire);
    
    /**
     * Mettre à jour un partenaire existant
     */
    Partenaire update(Long id, Partenaire partenaire);
    
    /**
     * Trouver un partenaire par son ID
     */
    Optional<Partenaire> findById(Long id);
    
    /**
     * Trouver un partenaire par son email
     */
    Optional<Partenaire> findByEmail(String email);
    
    /**
     * Lister tous les partenaires
     */
    List<Partenaire> findAll();
    
    /**
     * Lister les partenaires par domaine d'activité
     */
    List<Partenaire> findByDomaineActivite(String domaineActivite);
    
    /**
     * Lister les partenaires par statut
     */
    List<Partenaire> findByStatut(StatutPartenaire statut);
    
    /**
     * Rechercher des partenaires par nom
     */
    List<Partenaire> searchByNom(String nom);
    
    /**
     * Approuver un partenaire
     */
    Partenaire approuverPartenaire(Long id);
    
    /**
     * Suspendre un partenaire
     */
    Partenaire suspendrePartenaire(Long id);
    
    /**
     * Résilier un partenariat
     */
    Partenaire resilierPartenariat(Long id, String motif);
    
    /**
     * Ajouter des fonds au solde d'un partenaire
     */
    Partenaire ajouterFonds(Long id, Double montant);
    
    /**
     * Débiter des fonds du solde d'un partenaire
     */
    Partenaire debiterFonds(Long id, Double montant);
    
    /**
     * Supprimer un partenaire
     */
    void delete(Long id);
} 