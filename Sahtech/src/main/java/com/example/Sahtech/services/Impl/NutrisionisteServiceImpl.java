package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.entities.NutrisionisteContact;
import com.example.Sahtech.exceptions.ResourceNotFoundException;
import com.example.Sahtech.repositories.NutritionisteRepository;
import com.example.Sahtech.repositories.NutrisionisteContactRepository;
import com.example.Sahtech.services.NutrisionisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NutrisionisteServiceImpl implements NutrisionisteService {

    @Autowired
    private NutritionisteRepository nutrisionisteRepository;
    
    @Autowired
    private NutrisionisteContactRepository nutrisionisteContactRepository;

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
    
    @Override
    public List<Nutrisioniste> getNutrisionisteContactsByUserId(String userId) {
        // Récupérer tous les contacts de l'utilisateur
        List<NutrisionisteContact> contacts = nutrisionisteContactRepository.findByUtilisateurId(userId);
        
        if (contacts.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Récupérer les IDs des nutritionnistes contactés
        List<String> nutrisionisteIds = contacts.stream()
                .map(NutrisionisteContact::getNutrisionisteId)
                .collect(Collectors.toList());
        
        // Récupérer les nutritionnistes correspondants
        List<Nutrisioniste> nutritionistes = new ArrayList<>();
        
        for (String nutrisionisteId : nutrisionisteIds) {
            Optional<Nutrisioniste> nutrisioniste = nutrisionisteRepository.findById(nutrisionisteId);
            nutrisioniste.ifPresent(nutritionistes::add);
        }
        
        return nutritionistes;
    }
}
