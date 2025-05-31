package com.example.Sahtech.services.Impl.Users;

import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;
import com.example.Sahtech.repositories.Users.NutritionisteDetaille.NutritionisteRepository;
import com.example.Sahtech.services.Interfaces.Users.NutrisionisteService;
import org.springframework.stereotype.Service;

@Service
public class NutrisionisteServiceImpl implements NutrisionisteService {
    private final NutritionisteRepository nutritionistRepository;

    public NutrisionisteServiceImpl(NutritionisteRepository nutritionistRepository) {
        this.nutritionistRepository = nutritionistRepository;
    }

    @Override
    public Nutrisioniste save(Nutrisioniste nutritionist) {
        return nutritionistRepository.save(nutritionist);
    }

    @Override
    public boolean existsByEmail(String email) {
        return nutritionistRepository.existsByEmail(email);
    }
} 