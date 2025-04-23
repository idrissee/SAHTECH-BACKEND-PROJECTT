package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.entities.Recommendation;
import com.example.Sahtech.entities.Utilisateurs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    
    List<Recommendation> findByUtilisateur(Utilisateurs utilisateur);
    
    List<Recommendation> findByProduit(Produit produit);
    
    Optional<Recommendation> findByUtilisateurAndProduit(Utilisateurs utilisateur, Produit produit);
} 