package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.HistoriqueScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoriqueScanRepository extends MongoRepository<HistoriqueScan, Long> {
    
    // Trouver l'historique d'un utilisateur spécifique
    List<HistoriqueScan> findByUtilisateurId(Long utilisateurId);
    
    // Trouver les scans d'un produit spécifique
    List<HistoriqueScan> findByProduitId(Long produitId);
    
    // Trouver les scans par note NutriScore
    List<HistoriqueScan> findByNoteNutriScore(String noteNutriScore);
    
    // Trouver les scans favoris d'un utilisateur
    List<HistoriqueScan> findByUtilisateurIdAndEstFavoriTrue(Long utilisateurId);
    
    // Trouver les scans récents d'un utilisateur (des 30 derniers jours)
    List<HistoriqueScan> findByUtilisateurIdAndDateScanAfter(Long utilisateurId, LocalDateTime date);
    
    // Trouver les scans par impact sur la santé
    List<HistoriqueScan> findByImpactSante(String impactSante);
    
    // Trouver les scans contenant un additif spécifique
    List<HistoriqueScan> findByAdditifsDetectesContaining(String additif);
    
    // Trouver les scans par période
    List<HistoriqueScan> findByDateScanBetween(LocalDateTime startDate, LocalDateTime endDate);
} 