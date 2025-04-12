package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.HistoriqueScan;
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
    public List<HistoriqueScan> getFavorisUtilisateur(String utilisateurId) {
        return historiqueScanRepository.findByUtilisateurIdAndEstFavoriTrue(utilisateurId);
    }

    @Override
    public List<HistoriqueScan> getScansRecents(String utilisateurId, int jours) {
        LocalDateTime dateLimite = LocalDateTime.now().minusDays(jours);
        return historiqueScanRepository.findByUtilisateurIdAndDateScanAfter(utilisateurId, dateLimite);
    }

    @Override
    public List<HistoriqueScan> getScansByImpactSante(String impact) {
        return historiqueScanRepository.findByImpactSante(impact);
    }

    @Override
    public List<HistoriqueScan> getScansByAdditif(String additif) {
        return historiqueScanRepository.findByAdditifsDetectesContaining(additif);
    }

    @Override
    public List<HistoriqueScan> getScansByPeriode(LocalDateTime startDate, LocalDateTime endDate) {
        return historiqueScanRepository.findByDateScanBetween(startDate, endDate);
    }

    @Override
    public Map<String, Long> getStatistiquesNutriScore(String utilisateurId) {
        return historiqueScanRepository.findByUtilisateurId(utilisateurId)
                .stream()
                .collect(Collectors.groupingBy(
                        HistoriqueScan::getNoteNutriScore,
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> getStatistiquesImpactSante(String utilisateurId) {
        return historiqueScanRepository.findByUtilisateurId(utilisateurId)
                .stream()
                .collect(Collectors.groupingBy(
                        HistoriqueScan::getImpactSante,
                        Collectors.counting()
                ));
    }

    @Override
    public List<String> getAdditifsFrequents(String utilisateurId) {
        return historiqueScanRepository.findByUtilisateurId(utilisateurId)
                .stream()
                .flatMap(scan -> scan.getAdditifsDetectes().stream())
                .collect(Collectors.groupingBy(
                        additif -> additif,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public String getEvolutionSante(String utilisateurId) {
        List<HistoriqueScan> scans = historiqueScanRepository.findByUtilisateurId(utilisateurId);
        if (scans.isEmpty()) {
            return "Pas assez de données pour analyser l'évolution";
        }

        long bonsScans = scans.stream()
                .filter(scan -> "Bonne".equals(scan.getImpactSante()))
                .count();
        
        double pourcentageBons = (double) bonsScans / scans.size() * 100;
        
        return String.format("Sur %d scans, %.2f%% ont un impact positif sur la santé", 
                scans.size(), pourcentageBons);
    }

    @Override
    public HistoriqueScan updateFavori(String scanId, Boolean estFavori) {
        HistoriqueScan scan = historiqueScanRepository.findById(scanId)
                .orElseThrow(() -> new RuntimeException("Scan non trouvé"));
        scan.setEstFavori(estFavori);
        return historiqueScanRepository.save(scan);
    }

    @Override
    public HistoriqueScan addCommentaire(String scanId, String commentaire) {
        HistoriqueScan scan = historiqueScanRepository.findById(scanId)
                .orElseThrow(() -> new RuntimeException("Scan non trouvé"));
        scan.setCommentaireUtilisateur(commentaire);
        return historiqueScanRepository.save(scan);
    }
} 