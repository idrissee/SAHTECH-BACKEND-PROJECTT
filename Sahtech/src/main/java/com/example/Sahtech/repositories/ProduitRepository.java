package com.example.Sahtech.repositories;


import com.example.Sahtech.entities.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {
}
