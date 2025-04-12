package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.entities.Utilisateurs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoriqueScanRepository extends MongoRepository<HistoriqueScan, String> {

    // Trouver les historiques de scan par utilisateur
    List<HistoriqueScan> findByUtilisateur(Utilisateurs utilisateur);
    List<HistoriqueScan> findByUtilisateurId(String utilisateurId);
    
    // Trouver les historiques de scan par produit
    List<HistoriqueScan> findByProduit(Produit produit);
    List<HistoriqueScan> findByProduitId(String produitId);
    
    // Trouver les historiques de scan dans une plage de dates
    List<HistoriqueScan> findByDateScanBetween(LocalDateTime start, LocalDateTime end);
    
    // Trouver les historiques de scan marqués comme favoris
    List<HistoriqueScan> findByEstFavori(Boolean estFavori);
    List<HistoriqueScan> findByUtilisateurIdAndEstFavoriTrue(String utilisateurId);
    
    // Trouver les historiques de scan par note NutriScore
    List<HistoriqueScan> findByNoteNutriScore(String noteNutriScore);
    
    // Trouver les derniers scans de l'utilisateur (limité à 10)
    List<HistoriqueScan> findTop10ByUtilisateurOrderByDateScanDesc(Utilisateurs utilisateur);
    List<HistoriqueScan> findByUtilisateurIdAndDateScanAfter(String utilisateurId, LocalDateTime date);
    
    // Trouver les historiques de scan par impact santé
    List<HistoriqueScan> findByImpactSante(String impactSante);
    
    // Trouver les historiques de scan contenant un additif spécifique
    List<HistoriqueScan> findByAdditifsDetectesContaining(String additif);
    
    // Compter le nombre de scans par utilisateur
    Long countByUtilisateur(Utilisateurs utilisateur);
} 