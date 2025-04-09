package com.example.Sahtech.services;

import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.entities.Utilisateurs;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface HistoriqueScanService {
    // Opérations CRUD de base
    HistoriqueScan saveScan(HistoriqueScan scan);
    HistoriqueScan getScanById(Long id);
    List<HistoriqueScan> getAllScans();
    void deleteScan(Long id);
    
    // Opérations métier spécifiques
    List<HistoriqueScan> getHistoriqueUtilisateur(Long utilisateurId);
    List<HistoriqueScan> getScansProduit(Long produitId);
    List<HistoriqueScan> getScansByNutriScore(String note);
    List<HistoriqueScan> getFavorisUtilisateur(Long utilisateurId);
    List<HistoriqueScan> getScansRecents(Long utilisateurId, int jours);
    List<HistoriqueScan> getScansByImpactSante(String impact);
    List<HistoriqueScan> getScansByAdditif(String additif);
    List<HistoriqueScan> getScansByPeriode(LocalDateTime startDate, LocalDateTime endDate);
    
    // Nouvel ajout - Récupérer les utilisateurs qui ont scanné un produit spécifique
    List<Utilisateurs> getUtilisateursByProduit(Long produitId);
    
    // Opérations d'analyse
    Map<String, Long> getStatistiquesNutriScore(Long utilisateurId);
    Map<String, Long> getStatistiquesImpactSante(Long utilisateurId);
    List<String> getAdditifsFrequents(Long utilisateurId);
    String getEvolutionSante(Long utilisateurId);
    
    // Opérations de mise à jour
    HistoriqueScan updateFavori(Long scanId, Boolean estFavori);
    HistoriqueScan addCommentaire(Long scanId, String commentaire);
} 