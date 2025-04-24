package com.example.Sahtech.repositories.ProduitDetaille;

import com.example.Sahtech.entities.ProduitDetaille.Additifs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditifsRepository extends MongoRepository<Additifs,String> {

    Additifs findByCodeAdditif(String codeAdditif);
}

