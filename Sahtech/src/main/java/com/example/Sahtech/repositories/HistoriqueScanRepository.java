package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.HistoriqueScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoriqueScanRepository extends MongoRepository<HistoriqueScan, String> {
    
    // Trouver l'historique d'un utilisateur spécifique
    List<HistoriqueScan> findByUtilisateurId(String utilisateurId);
    
    // Trouver les scans d'un produit spécifique
    List<HistoriqueScan> findByProduitId(String produitId);
    
    // Trouver les scans par note NutriScore
    List<HistoriqueScan> findByNoteNutriScore(String noteNutriScore);
    
    // Trouver les scans récents d'un utilisateur (des 30 derniers jours)
    List<HistoriqueScan> findByUtilisateurIdAndDateScanAfter(String utilisateurId, LocalDateTime date);
    
    // Trouver les scans par impact sur la santé
    List<HistoriqueScan> findByImpactSante(String impactSante);
    
    // Trouver les scans contenant un additif spécifique
    List<HistoriqueScan> findByAdditifsDetectesContaining(String additif);
    
    // Trouver les scans par période
    List<HistoriqueScan> findByDateScanBetween(LocalDateTime startDate, LocalDateTime endDate);
} 