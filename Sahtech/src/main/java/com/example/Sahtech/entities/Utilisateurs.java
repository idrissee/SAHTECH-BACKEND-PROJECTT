package com.example.Sahtech.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "utilisateurs")
public class Utilisateurs extends Personne{

    @Id
    private Long id;


    private Date dateDeNaissence;

}
