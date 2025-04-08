package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Nutrisioniste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutrisionisteRepository extends MongoRepository<Nutrisioniste, Long> {
    
    // Trouver les nutritionnistes par localisation
    List<Nutrisioniste> findByLocalisationId(Long localisationId);
    
    // Trouver les nutritionnistes par spécialité
    List<Nutrisioniste> findBySpecialite(String specialite);
    
    // Trouver les nutritionnistes vérifiés
    List<Nutrisioniste> findByEstVerifie(Boolean estVerifie);
}
