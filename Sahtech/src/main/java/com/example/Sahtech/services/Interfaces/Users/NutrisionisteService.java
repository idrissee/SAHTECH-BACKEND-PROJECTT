package com.example.Sahtech.services.Interfaces.Users;

import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;

public interface NutrisionisteService {
    Nutrisioniste save(Nutrisioniste nutritionist);
    boolean existsByEmail(String email);
} 