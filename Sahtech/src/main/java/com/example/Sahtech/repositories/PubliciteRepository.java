package com.example.Sahtech.repositories;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import com.example.Sahtech.entities.Publicite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository pour l'entité Publicite
 */
@Repository
public interface PubliciteRepository extends MongoRepository<Publicite, Long> {
    
    /**
     * Trouver les publicités par statut
     */
    List<Publicite> findByStatusPublicite(StatusPublicite statusPublicite);
    
    /**
     * Trouver les publicités par état
     */
    List<Publicite> findByEtatPublicite(EtatPublicite etatPublicite);
    
    /**
     * Trouver les publicités par partenaire
     */
    List<Publicite> findByPartenaire_id(Long partenaireId);
    
    /**
     * Trouver les publicités par type
     */
    List<Publicite> findByTypePub(TypePublicite typePub);
    
    /**
     * Trouver les publicités actives à afficher (acceptées et publiées)
     */
    List<Publicite> findByStatusPubliciteAndEtatPublicite(StatusPublicite statusPublicite, EtatPublicite etatPublicite);
    
    /**
     * Trouver les publicités dont la période est en cours
     */
    List<Publicite> findByDateDebutBeforeAndDateFinAfter(Date dateDebut, Date dateFin);
    
    /**
     * Trouver les publicités par priorité (ordre décroissant)
     */
    List<Publicite> findByEtatPubliciteOrderByPrioriteDesc(EtatPublicite etatPublicite);
}
