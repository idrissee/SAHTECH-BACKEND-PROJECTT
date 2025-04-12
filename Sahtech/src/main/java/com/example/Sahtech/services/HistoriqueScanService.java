package com.example.Sahtech.services;

import com.example.Sahtech.entities.HistoriqueScan;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface HistoriqueScanService {
    // Opérations CRUD de base
    HistoriqueScan saveScan(HistoriqueScan scan);
    HistoriqueScan getScanById(String id);
    List<HistoriqueScan> getAllScans();
    void deleteScan(String id);
    
    // Opérations métier spécifiques
    List<HistoriqueScan> getHistoriqueUtilisateur(String utilisateurId);
    List<HistoriqueScan> getScansProduit(String produitId);
    List<HistoriqueScan> getScansByNutriScore(String note);
    List<HistoriqueScan> getFavorisUtilisateur(String utilisateurId);
    List<HistoriqueScan> getScansRecents(String utilisateurId, int jours);
    List<HistoriqueScan> getScansByImpactSante(String impact);
    List<HistoriqueScan> getScansByAdditif(String additif);
    List<HistoriqueScan> getScansByPeriode(LocalDateTime startDate, LocalDateTime endDate);
    
    // Opérations d'analyse
    Map<String, Long> getStatistiquesNutriScore(String utilisateurId);
    Map<String, Long> getStatistiquesImpactSante(String utilisateurId);
    List<String> getAdditifsFrequents(String utilisateurId);
    String getEvolutionSante(String utilisateurId);
    
    // Opérations de mise à jour
    HistoriqueScan updateFavori(String scanId, Boolean estFavori);
    HistoriqueScan addCommentaire(String scanId, String commentaire);
} 