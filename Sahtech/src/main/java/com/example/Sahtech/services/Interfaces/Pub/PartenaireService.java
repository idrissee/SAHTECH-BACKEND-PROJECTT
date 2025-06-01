package com.example.Sahtech.services.Interfaces.Pub;

import com.example.Sahtech.Enum.StatutPartenaire;
import com.example.Sahtech.entities.Pub.Partenaire;

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
    Partenaire update(String id, Partenaire partenaire);
    
    /**
     * Trouver un partenaire par son ID
     */
    Optional<Partenaire> findById(String id);
    
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
    Partenaire approuverPartenaire(String id);
    
    /**
     * Suspendre un partenaire
     */
    Partenaire suspendrePartenaire(String id);
    
    /**
     * Résilier un partenariat
     */
    Partenaire resilierPartenariat(String id, String motif);
    
    /**
     * Ajouter des fonds au solde d'un partenaire
     */
    Partenaire ajouterFonds(String id, Double montant);
    
    /**
     * Débiter des fonds du solde d'un partenaire
     */
    Partenaire debiterFonds(String id, Double montant);
    
    /**
     * Supprimer un partenaire
     */
    void delete(String id);
} 