package com.example.Sahtech.repositories.Users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.Sahtech.entities.Users.Utilisateurs;

import java.util.List;
import java.util.Optional;


@Repository
public interface UtilisateursRepository extends MongoRepository <Utilisateurs, String> {

    Optional<Utilisateurs> findByEmail(String email);
    List<Utilisateurs> findByNom(String nom);


    boolean existsByEmail(String email);
}
