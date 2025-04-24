package com.example.Sahtech.entities.Users.NutritionisteDetaille;

import com.example.Sahtech.entities.Users.Utilisateurs;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Document(collection = "nutrisioniste")
public class Nutrisioniste extends Utilisateurs {
    
    // Champs spécifiques au nutritionniste
    private String specialite;
    private String localisationId;
    private Boolean estVerifie;
    
    public Nutrisioniste() {
        super("NUTRITIONIST");
    }
} 