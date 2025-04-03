package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<Admin, Long> {
       Admin getAdminsById(Long id);
}
