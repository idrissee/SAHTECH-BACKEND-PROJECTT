package com.example.Sahtech.services;

import com.example.Sahtech.entities.Nutrisioniste;

import java.util.List;

public interface NutrisionisteService {

    List<Nutrisioniste> getAllNutrisionistes();

    Nutrisioniste getNutrisionisteById(String id);

    Nutrisioniste getNutrisionisteByEmail(String email);

    Nutrisioniste getNutrisionisteByTelephone(String telephone);

    Nutrisioniste createNutrisioniste(Nutrisioniste nutrisioniste);

    Nutrisioniste updateNutrisioniste(String id, Nutrisioniste nutrisioniste);

    void deleteNutrisioniste(String id);
}
