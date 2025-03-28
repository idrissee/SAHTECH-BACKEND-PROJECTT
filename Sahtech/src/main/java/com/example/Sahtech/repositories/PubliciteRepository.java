package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Publicite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PubliciteRepository extends MongoRepository< Publicite,Long> {
}
