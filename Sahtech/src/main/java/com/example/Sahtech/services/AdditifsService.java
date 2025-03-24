package com.example.Sahtech.services;

import com.example.Sahtech.entities.Additifs;

import java.util.List;
import java.util.Optional;

public interface AdditifsService {
    Additifs createAdditifs(Additifs additifs);

    List<Additifs> findAll();

    Optional<Additifs> findOnebyId(Long id);
}
