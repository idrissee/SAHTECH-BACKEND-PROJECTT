package com.example.Sahtech.repositories.Recommendation;

import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Recommendation.Recommendation;
import com.example.Sahtech.entities.Users.Utilisateurs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    List<Recommendation> findByUtilisateur(Utilisateurs utilisateur);
    Optional<Recommendation> findByUtilisateurAndProduit(Utilisateurs utilisateur, Produit produit);
} 