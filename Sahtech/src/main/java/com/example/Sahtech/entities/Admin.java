package com.example.Sahtech.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Admin")
public class Admin extends Personne {
@Id
private Long id;
}
