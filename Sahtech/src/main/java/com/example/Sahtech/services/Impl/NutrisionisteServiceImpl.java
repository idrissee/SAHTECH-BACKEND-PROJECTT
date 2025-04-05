package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.repositories.NutrisionisteRepository;
import com.example.Sahtech.services.NutrisionisteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NutrisionisteServiceImpl implements NutrisionisteService {
    
    private final NutrisionisteRepository nutrisionisteRepository;
    
    public NutrisionisteServiceImpl(NutrisionisteRepository nutrisionisteRepository) {
        this.nutrisionisteRepository = nutrisionisteRepository;
    }

    
    @Override
    public List<Nutrisioniste> findAll() {
        return StreamSupport.stream(nutrisionisteRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Nutrisioniste> findOneById(Long id) {
        return nutrisionisteRepository.findById(id);
    }
    
    @Override
    public boolean isExists(Long id) {
        return nutrisionisteRepository.existsById(id);
    }
    
    @Override
    public Nutrisioniste save(Nutrisioniste nutrisioniste) {
        return nutrisionisteRepository.save(nutrisioniste);
    }
    
    @Override
    public Nutrisioniste update(Long id, Nutrisioniste nutrisioniste) {
        if (!nutrisionisteRepository.existsById(id)) {
            return null;
        }
        
        nutrisioniste.setId(id);
        return nutrisionisteRepository.save(nutrisioniste);
    }
    
    @Override
    public void delete(Long id) {
        nutrisionisteRepository.deleteById(id);
    }
    
    @Override
    public List<Nutrisioniste> findByLocalisationId(Long localisationId) {
        return nutrisionisteRepository.findByLocalisationId(localisationId);
    }
    
    @Override
    public List<Nutrisioniste> findBySpecialite(String specialite) {
        return nutrisionisteRepository.findBySpecialite(specialite);
    }
    
    @Override
    public List<Nutrisioniste> findByEstVerifie(Boolean estVerifie) {
        return nutrisionisteRepository.findByEstVerifie(estVerifie);
    }
} 