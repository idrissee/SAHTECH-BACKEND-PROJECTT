package com.example.Sahtech.entities.Users.NutritionisteDetaille;

import com.example.Sahtech.entities.Users.Utilisateurs;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Document(collection = "nutrisioniste")
public class Nutrisioniste extends Utilisateurs {
    
    // Champs spécifiques au nutritionniste
    private String specialite;
    private String localisationId;
    private Boolean estVerifie;
    private String photoUrlDiplome;
    private Double latitude;
    private Double longitude;
    private String cabinetAddress;
    private List<String> proveAttestationType;
    
    @Field("nutritionist_daily_activities")
    private List<String> dailyActivities;
   
    public Nutrisioniste() {
        super("NUTRITIONIST");
    }
} 