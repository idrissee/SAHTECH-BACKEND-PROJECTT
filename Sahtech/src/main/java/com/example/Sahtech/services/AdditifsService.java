package com.example.Sahtech.services;

import com.example.Sahtech.entities.Additifs;

import java.util.List;
import java.util.Optional;

public interface AdditifsService {
    Additifs save(Additifs additifs);

    List<Additifs> findAll();

    Optional<Additifs> findOnebyId(Long id);

    boolean isExists(Long id);

    Additifs partialUpdate(Long id, Additifs additif);

    void delete(Long id);
}
