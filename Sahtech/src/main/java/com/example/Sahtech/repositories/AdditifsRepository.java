package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Additifs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditifsRepository extends MongoRepository<Additifs,Long> {

}
