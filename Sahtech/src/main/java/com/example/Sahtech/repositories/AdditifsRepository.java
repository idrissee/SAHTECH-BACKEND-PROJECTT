package com.example.Sahtech.repositories;

import com.example.Sahtech.entities.Additifs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditifsRepository extends CrudRepository<Additifs, Long> {
}
