package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.HistoriqueScanDto;
import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.AuthorizationService;
import com.example.Sahtech.services.HistoriqueScanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/HistoriqueScan")
public class HistoriqueScanController {

    private final HistoriqueScanService historiqueScanService;
    private final Mapper<HistoriqueScan, HistoriqueScanDto> historiqueScanMapper;
    
    @Autowired
    private AuthorizationService authorizationService;

    public HistoriqueScanController(HistoriqueScanService historiqueScanService, 
                                  Mapper<HistoriqueScan, HistoriqueScanDto> historiqueScanMapper) {
        this.historiqueScanService = historiqueScanService;
        this.historiqueScanMapper = historiqueScanMapper;
    }

    @PostMapping
    public ResponseEntity<HistoriqueScanDto> saveScan(@RequestBody HistoriqueScanDto scanDto) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")) ) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        HistoriqueScan scan = historiqueScanMapper.mapFrom(scanDto);
        HistoriqueScan savedScan = historiqueScanService.saveScan(scan);
        return new ResponseEntity<>(historiqueScanMapper.mapTo(savedScan), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueScanDto> getScanById(@PathVariable String id) {
        // Cette méthode est réservée aux admins (géré par SecurityConfig)
        HistoriqueScan scan = historiqueScanService.getScanById(id);
        return ResponseEntity.ok(historiqueScanMapper.mapTo(scan));
    }

    @GetMapping("/All")
    public ResponseEntity<List<HistoriqueScanDto>> getAllScans() {
        // Cette méthode est réservée aux admins (géré par SecurityConfig)
        // Double vérification que l'utilisateur est admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        List<HistoriqueScanDto> scanDtos = historiqueScanService.getAllScans().stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scanDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScan(@PathVariable String id) {
        // Cette méthode est réservée aux admins (géré par SecurityConfig)
        historiqueScanService.deleteScan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<List<HistoriqueScanDto>> getHistoriqueUtilisateur(
            @PathVariable String id, 
            HttpServletRequest request) {
        
        // Vérifier si l'utilisateur est autorisé (admin ou lui-même)
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        List<HistoriqueScanDto> scanDtos = historiqueScanService.getHistoriqueUtilisateur(id).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scanDtos, HttpStatus.OK);
    }

    @GetMapping("/recents/{utilisateurId}")
    public ResponseEntity<List<HistoriqueScanDto>> getScansRecents(
            @PathVariable String utilisateurId,
            @RequestParam(defaultValue = "30") int jours,
            HttpServletRequest request) {
        
        // Vérifier si l'utilisateur est autorisé (admin ou lui-même)
        if (!authorizationService.isAuthorizedToAccessResource(utilisateurId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        List<HistoriqueScanDto> scanDtos = historiqueScanService.getScansRecents(utilisateurId, jours).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scanDtos, HttpStatus.OK);
    }



    // Les méthodes suivantes sont réservées aux admins (géré par SecurityConfig)
    
    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<HistoriqueScanDto>> getScansProduit(@PathVariable String produitId) {
        List<HistoriqueScanDto> scanDtos = historiqueScanService.getScansProduit(produitId).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scanDtos, HttpStatus.OK);
    }

    @GetMapping("/nutriscore/{note}")
    public ResponseEntity<List<HistoriqueScanDto>> getScansByNutriScore(@PathVariable String note) {
        List<HistoriqueScanDto> scanDtos = historiqueScanService.getScansByNutriScore(note).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scanDtos, HttpStatus.OK);
    }


    @GetMapping("/periode")
    public ResponseEntity<List<HistoriqueScanDto>> getScansByPeriode(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<HistoriqueScanDto> scanDtos = historiqueScanService.getScansByPeriode(startDate, endDate).stream()
                .map(historiqueScanMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scanDtos, HttpStatus.OK);
    }

    @PutMapping("/commentaire/{scanId}")
    public ResponseEntity<HistoriqueScanDto> addCommentaire(
            @PathVariable String scanId,
            @RequestParam String commentaire) {
        HistoriqueScan updatedScan = historiqueScanService.addCommentaire(scanId, commentaire);
        return new ResponseEntity<>(historiqueScanMapper.mapTo(updatedScan), HttpStatus.OK);
    }
} 