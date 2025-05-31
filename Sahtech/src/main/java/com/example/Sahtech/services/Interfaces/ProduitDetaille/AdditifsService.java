package com.example.Sahtech.services.Interfaces.ProduitDetaille;

import com.example.Sahtech.entities.ProduitDetaille.Additifs;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AdditifsService {


    Additifs save(Additifs additifs);

    List<Additifs> findAll();

    Optional<Additifs> findOnebyId(String id);

    boolean isExists(String id);

    Additifs partialUpdate(String id, Additifs additif);

    void delete(String id);

    Additifs getBycodeAdditif(String nom);

    void loadAndSaveAdditifs() throws IOException;
}
