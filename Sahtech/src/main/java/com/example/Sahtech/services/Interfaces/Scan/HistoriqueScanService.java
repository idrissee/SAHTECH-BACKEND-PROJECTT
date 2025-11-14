package com.example.Sahtech.services.Interfaces.Scan;

import com.example.Sahtech.entities.Scan.HistoriqueScan;
import com.example.Sahtech.entities.Users.Utilisateurs;
import java.time.LocalDateTime;
import java.util.List;

public interface HistoriqueScanService {
    // Opérations CRUD de base
    HistoriqueScan saveScan(HistoriqueScan scan);
    HistoriqueScan getScanById(String id);
    List<HistoriqueScan> getAllScans();
    void deleteScan(String id);
    
    // Opérations métier spécifiques
    List<HistoriqueScan> getHistoriqueUtilisateur(String utilisateurId);
    List<HistoriqueScan> getScansProduit(String produitId);
    List<HistoriqueScan> getScansByRecommendationType(String recommendationType);
    List<HistoriqueScan> getScansRecents(String utilisateurId, int jours);
    List<HistoriqueScan> getScansByPeriode(LocalDateTime startDate, LocalDateTime endDate);
    
    // Nouvel ajout - Récupérer les utilisateurs qui ont scanné un produit spécifique
    List<Utilisateurs> getUtilisateursByProduit(String produitId);
    
    // Vérifier si un utilisateur a scanné un produit spécifique
    boolean hasUserScannedProduct(String utilisateurId, String produitId);

    void incrementUserScanCount(String utilisateurId);
}