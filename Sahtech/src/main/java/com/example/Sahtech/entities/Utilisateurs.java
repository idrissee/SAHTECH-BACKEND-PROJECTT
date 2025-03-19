package com.example.Sahtech.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "utilisateurs")
public class Utilisateurs extends Personne{

    @Id
    private Long id;


    private Date dateDeNaissence;

}
