package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.time.LocalDateTime;

@Document(collection = "nutritioniste_contacts")
@CompoundIndex(def = "{'utilisateurId': 1, 'nutrisionisteId': 1}", unique = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NutrisionisteContact {
    
    @Id
    private String id;
    
    private String utilisateurId;
    
    private String nutrisionisteId;
    
    private LocalDateTime firstContactDate;
    
    private LocalDateTime lastContactDate;
    
    private int contactCount;
} 