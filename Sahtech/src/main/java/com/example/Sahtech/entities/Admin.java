package com.example.Sahtech.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Document(collection = "admin")
public class Admin extends Utilisateurs {
    
    // Champs spécifiques à Admin peuvent être ajoutés ici
    
    public Admin() {
        super("ADMIN");
    }
} 