package com.example.Sahtech.services.Impl;

import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.NutriScore;
import com.example.Sahtech.repositories.NutriScoreRepository;
import com.example.Sahtech.services.NutriScoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NutriScoreServiceImpl implements NutriScoreService {

    private final NutriScoreRepository nutriScoreRepository;

    public NutriScoreServiceImpl(NutriScoreRepository nutriScoreRepository) {
        this.nutriScoreRepository = nutriScoreRepository;
    }


    @Override
    public List<NutriScore> findAll() {
        return StreamSupport.stream(nutriScoreRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NutriScore> findOneById(Long id) {
        return nutriScoreRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return nutriScoreRepository.existsById(id);
    }

    @Override
    public NutriScore save(NutriScore nutriScore) {
        return nutriScoreRepository.save(nutriScore);
    }

    @Override
    public NutriScore update(Long id, NutriScore nutriScore) {
        Optional<NutriScore> existingNutriScore = nutriScoreRepository.findById(id);
        
        if (existingNutriScore.isEmpty()) {
            return null;
        }
        
        nutriScore.setId(id);
        return nutriScoreRepository.save(nutriScore);
    }

    @Override
    public void delete(Long id) {
        nutriScoreRepository.deleteById(id);
    }

    @Override
    public List<NutriScore> findByValeur(ValeurNutriScore valeur) {
        return nutriScoreRepository.findByValeur(valeur);
    }
} 