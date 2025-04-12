package com.example.Sahtech.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "nutritionistes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nutrisioniste extends Utilisateurs {

    @Id
    private String id;

    private String specialite;

}
