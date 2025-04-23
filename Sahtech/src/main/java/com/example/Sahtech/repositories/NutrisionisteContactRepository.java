package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.NutrisionisteContact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutrisionisteContactRepository extends MongoRepository<NutrisionisteContact, String> {
    
    List<NutrisionisteContact> findByUtilisateurId(String utilisateurId);
    
    List<NutrisionisteContact> findByNutrisionisteId(String nutrisionisteId);
} 