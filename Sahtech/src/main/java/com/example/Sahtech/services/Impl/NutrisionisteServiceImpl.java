package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.repositories.NutritionisteRepository;
import com.example.Sahtech.services.NutrisionisteService;
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
    public Nutrisioniste getNutrisionisteById(Long id) {
        return nutrisionisteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutrisioniste introuvable avec l'ID: " + id));
    }

    @Override
    public Nutrisioniste getNutrisionisteByEmail(String email) {
        return nutrisionisteRepository.findAll()
                .stream()
                .filter(n -> email.equalsIgnoreCase(n.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nutrisioniste introuvable avec l'email: " + email));
    }

    @Override
    public Nutrisioniste getNutrisionisteByTelephone(String telephone) {
        return nutrisionisteRepository.findAll()
                .stream()
                .filter(n -> telephone.equals(n.getTelephone()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nutrisioniste introuvable avec le téléphone: " + telephone));
    }

    @Override
    public Nutrisioniste createNutrisioniste(Nutrisioniste nutrisioniste) {
        return nutrisionisteRepository.save(nutrisioniste);
    }

    @Override
    public Nutrisioniste updateNutrisioniste(Long id, Nutrisioniste updatedNutrisioniste) {
        Nutrisioniste existing = getNutrisionisteById(id);
        updatedNutrisioniste.setId(existing.getId()); // garder l'ID original
        return nutrisionisteRepository.save(updatedNutrisioniste);
    }

    @Override
    public void deleteNutrisioniste(Long id) {
        nutrisionisteRepository.deleteById(id);
    }
}
