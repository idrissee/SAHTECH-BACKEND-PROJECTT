package com.example.Sahtech.services.Impl.Scan;

import com.example.Sahtech.entities.Scan.HistoriqueScan;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.Scan.HistoriqueScanRepository;
import com.example.Sahtech.services.Interfaces.Scan.HistoriqueScanService;
import com.example.Sahtech.services.Interfaces.Users.UtilisateursService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class HistoriqueScanServiceImpl implements HistoriqueScanService {

    private final HistoriqueScanRepository historiqueScanRepository;
    private final UtilisateursService utilisateursService;
    private static final Logger logger = Logger.getLogger(HistoriqueScanServiceImpl.class.getName());

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
    public List<HistoriqueScan> getScansByRecommendationType(String recommendationType) {
        return historiqueScanRepository.findByRecommendationType(recommendationType);
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
    public List<Utilisateurs> getUtilisateursByProduit(String produitId) {
        List<HistoriqueScan> scans = historiqueScanRepository.findByProduitId(produitId);
        return scans.stream()
                .map(HistoriqueScan::getUtilisateur)
                .distinct() // Pour éviter les doublons (si un utilisateur a scanné plusieurs fois le même produit)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasUserScannedProduct(String utilisateurId, String produitId) {
        // Récupérer l'historique des scans de l'utilisateur
        List<HistoriqueScan> scansUtilisateur = historiqueScanRepository.findByUtilisateurId(utilisateurId);

        // Vérifier si l'un des scans correspond au produit demandé
        return scansUtilisateur.stream()
                .anyMatch(scan -> produitId.equals(scan.getProduit().getId()));
    }

    @Override
    public void incrementUserScanCount(String utilisateurId) {
        Utilisateurs utilisateur = utilisateursService.getUtilisateurById(utilisateurId);
        if (utilisateur != null) {
            utilisateur.setCountScans(utilisateur.getCountScans() + 1);
            utilisateursService.updateUtilisateur(utilisateurId, utilisateur);
        }
    }
}