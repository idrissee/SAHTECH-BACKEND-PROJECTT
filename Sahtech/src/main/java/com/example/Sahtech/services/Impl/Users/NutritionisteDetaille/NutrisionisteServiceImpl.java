package com.example.Sahtech.services.Impl.Users.NutritionisteDetaille;

import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;
import com.example.Sahtech.exceptions.ResourceNotFoundException;
import com.example.Sahtech.repositories.Users.NutritionisteDetaille.NutritionisteRepository;
import com.example.Sahtech.services.Interfaces.Users.NutritionisteDetaille.NutrisionisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutrisionisteServiceImpl implements NutrisionisteService {

    @Autowired
    private NutritionisteRepository nutrisionisteRepository;


    @Override
    public List<Nutrisioniste> getAllNutrisionistes() {
        return nutrisionisteRepository.findAll();
    }

    @Override
    public Nutrisioniste getNutrisionisteById(String id) {
        Optional<Nutrisioniste> nutrisioniste = nutrisionisteRepository.findById(id);
        return nutrisioniste.orElse(null);
    }

    @Override
    public Nutrisioniste getNutrisionisteByEmail(String email) {
        Optional<Nutrisioniste> nutrisioniste = nutrisionisteRepository.findByEmail(email);
        return nutrisioniste.orElse(null);
    }

    @Override
    public Nutrisioniste getNutrisionisteByTelephone(String telephone) {
        return nutrisionisteRepository.findAll()
                .stream()
                .filter(n -> telephone.equals(n.getNumTelephone().toString()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Nutrisioniste", "téléphone", telephone));
    }

    @Override
    public Nutrisioniste createNutrisioniste(Nutrisioniste nutrisioniste) {
        return nutrisionisteRepository.save(nutrisioniste);
    }

    @Override
    public Nutrisioniste updateNutrisioniste(String id, Nutrisioniste nutrisioniste) {
        if (nutrisionisteRepository.existsById(id)) {
            nutrisioniste.setId(id);
            return nutrisionisteRepository.save(nutrisioniste);
        }
        return null;
    }

    @Override
    public boolean deleteNutrisioniste(String id) {
        if (nutrisionisteRepository.existsById(id)) {
            nutrisionisteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Nutrisioniste> getNutrisionistesBySpecialite(String specialite) {
        return nutrisionisteRepository.findBySpecialite(specialite);
    }

}
