package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.HistoriqueScanDto;
import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.HistoriqueScanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historique")
public class HistoriqueScanController {

    private final HistoriqueScanService historiqueScanService;
    private final Mapper<HistoriqueScan, HistoriqueScanDto> historiqueScanMapper;

    public HistoriqueScanController(HistoriqueScanService historiqueScanService, 
                                  Mapper<HistoriqueScan, HistoriqueScanDto> historiqueScanMapper) {
        this.historiqueScanService = historiqueScanService;
        this.historiqueScanMapper = historiqueScanMapper;
    }

    @PostMapping
    public ResponseEntity<HistoriqueScanDto> saveScan(@RequestBody HistoriqueScanDto scanDto) {
        HistoriqueScan scan = historiqueScanMapper.mapFrom(scanDto);
        HistoriqueScan savedScan = historiqueScanService.saveScan(scan);
        return new ResponseEntity<>(historiqueScanMapper.mapTo(savedScan), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueScanDto> getScanById(@PathVariable String id) {
        HistoriqueScan scan = historiqueScanService.getScanById(id);
        return ResponseEntity.ok(historiqueScanMapper.mapTo(scan));
    }

    @GetMapping
    public List<HistoriqueScanDto> getAllScans() {
        return historiqueScanService.getAllScans().stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScan(@PathVariable String id) {
        historiqueScanService.deleteScan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<HistoriqueScanDto> getHistoriqueUtilisateur(@PathVariable String utilisateurId) {
        return historiqueScanService.getHistoriqueUtilisateur(utilisateurId).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/produit/{produitId}")
    public List<HistoriqueScanDto> getScansProduit(@PathVariable String produitId) {
        return historiqueScanService.getScansProduit(produitId).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/nutriscore/{note}")
    public List<HistoriqueScanDto> getScansByNutriScore(@PathVariable String note) {
        return historiqueScanService.getScansByNutriScore(note).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/favoris/{utilisateurId}")
    public List<HistoriqueScanDto> getFavorisUtilisateur(@PathVariable String utilisateurId) {
        return historiqueScanService.getFavorisUtilisateur(utilisateurId).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/recents/{utilisateurId}")
    public List<HistoriqueScanDto> getScansRecents(
            @PathVariable String utilisateurId,
            @RequestParam(defaultValue = "30") int jours) {
        return historiqueScanService.getScansRecents(utilisateurId, jours).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/impact/{impact}")
    public List<HistoriqueScanDto> getScansByImpactSante(@PathVariable String impact) {
        return historiqueScanService.getScansByImpactSante(impact).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/additif/{additif}")
    public List<HistoriqueScanDto> getScansByAdditif(@PathVariable String additif) {
        return historiqueScanService.getScansByAdditif(additif).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/periode")
    public List<HistoriqueScanDto> getScansByPeriode(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return historiqueScanService.getScansByPeriode(startDate, endDate).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/statistiques/nutriscore/{utilisateurId}")
    public Map<String, Long> getStatistiquesNutriScore(@PathVariable String utilisateurId) {
        return historiqueScanService.getStatistiquesNutriScore(utilisateurId);
    }

    @GetMapping("/statistiques/impact/{utilisateurId}")
    public Map<String, Long> getStatistiquesImpactSante(@PathVariable String utilisateurId) {
        return historiqueScanService.getStatistiquesImpactSante(utilisateurId);
    }

    @GetMapping("/additifs-frequents/{utilisateurId}")
    public List<String> getAdditifsFrequents(@PathVariable String utilisateurId) {
        return historiqueScanService.getAdditifsFrequents(utilisateurId);
    }

    @GetMapping("/evolution/{utilisateurId}")
    public String getEvolutionSante(@PathVariable String utilisateurId) {
        return historiqueScanService.getEvolutionSante(utilisateurId);
    }

    @PutMapping("/favori/{scanId}")
    public HistoriqueScanDto updateFavori(
            @PathVariable String scanId,
            @RequestParam Boolean estFavori) {
        HistoriqueScan updatedScan = historiqueScanService.updateFavori(scanId, estFavori);
        return historiqueScanMapper.mapTo(updatedScan);
    }

    @PutMapping("/commentaire/{scanId}")
    public HistoriqueScanDto addCommentaire(
            @PathVariable String scanId,
            @RequestParam String commentaire) {
        HistoriqueScan updatedScan = historiqueScanService.addCommentaire(scanId, commentaire);
        return historiqueScanMapper.mapTo(updatedScan);
    }
} 