package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Additifs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdditifsRepository extends MongoRepository<Additifs,String> {

    Optional<Additifs> findByNomAdditif(String name);
}
