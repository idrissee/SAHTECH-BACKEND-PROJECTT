package com.example.Sahtech.services;

import com.example.Sahtech.entities.Additifs;

import java.util.List;
import java.util.Optional;

public interface AdditifsService {
    Additifs save(Additifs additifs);

    List<Additifs> findAll();

    Optional<Additifs> findOnebyId(String id);

    boolean isExists(String id);

    Additifs partialUpdate(String id, Additifs additif);

    void delete(String id);

    Optional<Additifs> getByNom(String nom);
}
