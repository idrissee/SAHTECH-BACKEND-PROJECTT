package com.example.Sahtech.repositories.Users;

import com.example.Sahtech.entities.Users.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
    boolean existsByEmail(String email);
}
