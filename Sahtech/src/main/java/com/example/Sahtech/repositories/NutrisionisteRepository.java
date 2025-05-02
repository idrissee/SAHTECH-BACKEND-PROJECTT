package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Nutrisioniste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutrisionisteRepository extends MongoRepository<Nutrisioniste, String> {

    List<Nutrisioniste> findByLocalisationId(String localisationId);

    List<Nutrisioniste> findBySpecialite(String specialite);

    List<Nutrisioniste> findByEstVerifie(Boolean estVerifie);

    Optional<Nutrisioniste> findByEmail(String email);

    boolean existsByEmail(String email);
}

