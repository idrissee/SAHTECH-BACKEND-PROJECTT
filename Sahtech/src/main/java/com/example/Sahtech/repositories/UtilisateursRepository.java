package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.Sahtech.entities.Utilisateurs;

import java.util.List;
import java.util.Optional;


@Repository
public interface UtilisateursRepository extends MongoRepository <Utilisateurs, String> {

    Optional<Utilisateurs> findByEmail(String email);
    List<Utilisateurs> findByNom(String nom);


    boolean existsByEmail(String email);
}
