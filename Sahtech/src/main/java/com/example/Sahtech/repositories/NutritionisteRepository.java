package com.example.Sahtech.repositories;


import com.example.Sahtech.entities.Nutrisioniste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionisteRepository extends MongoRepository<Nutrisioniste,Long>{

    List<Nutrisioniste> findByLocalisationId(Long localisationId);

    List<Nutrisioniste> findBySpecialite(String specialite);

    List<Nutrisioniste> findByEstVerifie(Boolean estVerifie);
}

