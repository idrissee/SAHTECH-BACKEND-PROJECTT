package com.example.Sahtech.services.Interfaces.Pub;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import com.example.Sahtech.entities.Pub.Publicite;

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
    Publicite update(String id, Publicite publicite);
    
    /**
     * Trouver une publicité par son ID
     */
    Optional<Publicite> findById(String id);
    
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
    List<Publicite> findByPartenaireId(String partenaireId);
    
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
    Publicite accepterPublicite(String id);
    
    /**
     * Rejeter une publicité
     */
    Publicite rejeterPublicite(String id, String motif);
    
    /**
     * Activer une publicité
     */
    Publicite activerPublicite(String id);
    
    /**
     * Désactiver une publicité
     */
    Publicite desactiverPublicite(String id);


    
    /**
     * Supprimer une publicité
     */
    void delete(String id);

    /**
     * Mettre à jour l'URL de la photo d'une publicité
     */
    Publicite setPhotoUrl(String id, String photoUrl);
} 