package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Ingrediants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngrediantsRepository  extends MongoRepository<Ingrediants, String> {
}
