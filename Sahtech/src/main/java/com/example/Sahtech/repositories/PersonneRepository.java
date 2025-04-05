package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.entities.Personne;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonneRepository extends MongoRepository<Personne, Long>{
}
