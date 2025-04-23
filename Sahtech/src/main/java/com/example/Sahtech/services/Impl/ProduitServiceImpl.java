package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.entities.ProduitRecommendation;
import com.example.Sahtech.repositories.AdditifsRepository;
import com.example.Sahtech.repositories.ProduitRepository;
import com.example.Sahtech.repositories.ProduitRecommendationRepository;
import com.example.Sahtech.services.ProduitService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProduitServiceImpl implements ProduitService {

    ProduitRepository produitRepository;
    private final AdditifsRepository additifRepository;
    private final ProduitRecommendationRepository recommendationRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository, 
                             AdditifsRepository additifRepository,
                             ProduitRecommendationRepository recommendationRepository) {
        this.produitRepository = produitRepository;
        this.additifRepository = additifRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Produit createProduit(Produit produit) {
        Produit savedProduit = produitRepository.save(produit);
        return savedProduit;
    }

    @Override
    public List<Produit> findAll() {
        return StreamSupport.stream(produitRepository
                .findAll()
                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Produit> findOnebyId(String id) {
        return produitRepository.findById(id);
    }

    @Override
    public Optional<Produit> findByCodeBarre(String codeBarre) {
        return produitRepository.findByCodeBarre(codeBarre);
    }

    @Override
    public boolean isExists(String id) {
        return produitRepository.existsById(id);
    }

    @Override
    public Produit partialUpdate(String id, Produit produit) {

        produit.setId(id);

        return produitRepository.findById(id).map(exisitingProduit ->{
            Optional.ofNullable(produit.getNom()).ifPresent(exisitingProduit::setNom);
            Optional.ofNullable(produit.getDescription()).ifPresent(exisitingProduit::setDescription);
            return produitRepository.save(exisitingProduit);
        }).orElseThrow(() -> new RuntimeException("Produit not found"));
    }

    @Override
    public void delete(String id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit save(Produit produit) {
       return  produitRepository.save(produit);
    }
    
    @Override
    public boolean saveRecommendation(String productId, String userId, String recommendation) {
        try {
            // Vérifier si une recommandation existe déjà
            Optional<ProduitRecommendation> existingReco = 
                recommendationRepository.findByProduitIdAndUtilisateurId(productId, userId);
            
            ProduitRecommendation produitRecommendation;
            LocalDateTime now = LocalDateTime.now();
            
            if (existingReco.isPresent()) {
                // Mettre à jour la recommandation existante
                produitRecommendation = existingReco.get();
                produitRecommendation.setRecommendation(recommendation);
                produitRecommendation.setUpdatedAt(now);
            } else {
                // Créer une nouvelle recommandation
                produitRecommendation = ProduitRecommendation.builder()
                    .produitId(productId)
                    .utilisateurId(userId)
                    .recommendation(recommendation)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            }
            
            recommendationRepository.save(produitRecommendation);
            return true;
        } catch (Exception e) {
            // Logger l'erreur
            System.err.println("Erreur lors de l'enregistrement de la recommandation: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String getRecommendation(String productId, String userId) {
        Optional<ProduitRecommendation> recommendation = 
            recommendationRepository.findByProduitIdAndUtilisateurId(productId, userId);
        
        return recommendation.map(ProduitRecommendation::getRecommendation).orElse(null);
    }
}
