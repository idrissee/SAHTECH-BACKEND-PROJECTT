package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, Long> {
    Admin findByEmail(String email);
}
