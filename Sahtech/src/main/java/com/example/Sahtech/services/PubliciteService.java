package com.example.Sahtech.services;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import com.example.Sahtech.entities.Publicite;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des publicités
 */
public interface PubliciteService {
    
    /**
     * Créer une nouvelle publicité
     */
    Publicite save(Publicite publicite);
    
    /**
     * Mettre à jour une publicité existante
     */
    Publicite update(Long id, Publicite publicite);
    
    /**
     * Trouver une publicité par son ID
     */
    Optional<Publicite> findById(Long id);
    
    /**
     * Lister toutes les publicités
     */
    List<Publicite> findAll();
    
    /**
     * Lister les publicités par statut
     */
    List<Publicite> findByStatusPublicite(StatusPublicite statusPublicite);
    
    /**
     * Lister les publicités par état
     */
    List<Publicite> findByEtatPublicite(EtatPublicite etatPublicite);
    
    /**
     * Lister les publicités par partenaire
     */
    List<Publicite> findByPartenaireId(Long partenaireId);
    
    /**
     * Lister les publicités par type
     */
    List<Publicite> findByTypePub(TypePublicite typePub);
    
    /**
     * Trouver les publicités actives (acceptées et publiées)
     */
    List<Publicite> findActivePublicites();
    
    /**
     * Trouver les publicités dont la période est en cours
     */
    List<Publicite> findCurrentPublicites();
    
    /**
     * Accepter une publicité
     */
    Publicite accepterPublicite(Long id);
    
    /**
     * Rejeter une publicité
     */
    Publicite rejeterPublicite(Long id, String motif);
    
    /**
     * Activer une publicité
     */
    Publicite activerPublicite(Long id);
    
    /**
     * Désactiver une publicité
     */
    Publicite desactiverPublicite(Long id);
    
    /**
     * Mettre à jour les statistiques d'une publicité (impressions, clics)
     */
    Publicite updateStatistiques(Long id, boolean isClicked);
    
    /**
     * Trouver les publicités à afficher pour un utilisateur
     */
    List<Publicite> findPublicitesToDisplay(Long utilisateurId, String emplacement, int limit);
    
    /**
     * Supprimer une publicité
     */
    void delete(Long id);
} 