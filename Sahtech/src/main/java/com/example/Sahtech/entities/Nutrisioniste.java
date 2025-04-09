package com.example.Sahtech.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collation = "Nutrisioniste")
public class Nutrisioniste extends Utilisateurs {

    @Id
    private Long id;

    private String Specialite;

}
