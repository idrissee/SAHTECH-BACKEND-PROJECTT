package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.Sahtech.entities.Utilisateurs;

import java.util.List;


@Repository
public interface UtilisateursRepository extends MongoRepository <Utilisateurs, Long> {

    List<Utilisateurs> findByEmail(String email);


}
