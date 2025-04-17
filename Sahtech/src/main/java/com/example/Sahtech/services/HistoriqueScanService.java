package com.example.Sahtech.services;

import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.entities.Utilisateurs;
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
    List<HistoriqueScan> getScansRecents(String utilisateurId, int jours);
    List<HistoriqueScan> getScansByPeriode(LocalDateTime startDate, LocalDateTime endDate);
    
    // Nouvel ajout - Récupérer les utilisateurs qui ont scanné un produit spécifique
    List<Utilisateurs> getUtilisateursByProduit(String produitId);
    
    // Opérations de mise à jour
    HistoriqueScan addCommentaire(String scanId, String commentaire);
} 