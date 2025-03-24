package com.example.Sahtech.services;

import com.example.Sahtech.entities.Additifs;

import java.util.List;

public interface AdditifsService {
    Additifs createAdditifs(Additifs additifs);

    List<Additifs> findAll();
}
