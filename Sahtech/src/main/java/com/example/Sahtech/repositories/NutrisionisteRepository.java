package com.example.Sahtech.repositories;


import com.example.Sahtech.entities.Nutrisioniste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutrisionisteRepository extends MongoRepository<Nutrisioniste, Long> {
}
