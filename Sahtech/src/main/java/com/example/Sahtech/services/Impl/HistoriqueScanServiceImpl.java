package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.HistoriqueScanRepository;
import com.example.Sahtech.services.HistoriqueScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class HistoriqueScanServiceImpl implements HistoriqueScanService {

    private final HistoriqueScanRepository historiqueScanRepository;

    @Override
    public HistoriqueScan saveScan(HistoriqueScan scan) {
        return historiqueScanRepository.save(scan);
    }

    @Override
    public HistoriqueScan getScanById(String id) {
        return historiqueScanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scan non trouvé"));
    }

    @Override
    public List<HistoriqueScan> getAllScans() {
        return StreamSupport.stream(historiqueScanRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteScan(String id) {
        historiqueScanRepository.deleteById(id);
    }

    @Override
    public List<HistoriqueScan> getHistoriqueUtilisateur(String utilisateurId) {
        return historiqueScanRepository.findByUtilisateurId(utilisateurId);
    }

    @Override
    public List<HistoriqueScan> getScansProduit(String produitId) {
        return historiqueScanRepository.findByProduitId(produitId);
    }

    @Override
    public List<HistoriqueScan> getScansByNutriScore(String note) {
        return historiqueScanRepository.findByNoteNutriScore(note);
    }

    @Override
    public List<HistoriqueScan> getScansRecents(String utilisateurId, int jours) {
        LocalDateTime dateLimite = LocalDateTime.now().minusDays(jours);
        return historiqueScanRepository.findByUtilisateurIdAndDateScanAfter(utilisateurId, dateLimite);
    }

    @Override
    public List<HistoriqueScan> getScansByPeriode(LocalDateTime startDate, LocalDateTime endDate) {
        return historiqueScanRepository.findByDateScanBetween(startDate, endDate);
    }

    @Override
    public HistoriqueScan addCommentaire(String scanId, String commentaire) {
        HistoriqueScan scan = historiqueScanRepository.findById(scanId)
                .orElseThrow(() -> new RuntimeException("Scan non trouvé"));
        scan.setCommentaireUtilisateur(commentaire);
        return historiqueScanRepository.save(scan);
    }

    @Override
    public List<Utilisateurs> getUtilisateursByProduit(String produitId) {
        List<HistoriqueScan> scans = historiqueScanRepository.findByProduitId(produitId);
        return scans.stream()
                .map(HistoriqueScan::getUtilisateur)
                .distinct() // Pour éviter les doublons (si un utilisateur a scanné plusieurs fois le même produit)
                .collect(Collectors.toList());
    }
    
    @Override
    public int countScannedProductsByUser(String userId) {
        List<HistoriqueScan> scans = historiqueScanRepository.findByUtilisateurId(userId);
        // Compter les produits distincts
        return (int) scans.stream()
                .map(scan -> scan.getProduit().getId())
                .distinct()
                .count();
    }
} 