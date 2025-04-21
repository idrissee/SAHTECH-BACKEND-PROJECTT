package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.ProduitRecommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRecommendationRepository extends MongoRepository<ProduitRecommendation, String> {
    
    Optional<ProduitRecommendation> findByProduitIdAndUtilisateurId(String produitId, String utilisateurId);
    
} 