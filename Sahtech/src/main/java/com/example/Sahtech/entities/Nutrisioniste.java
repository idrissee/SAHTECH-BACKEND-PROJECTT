package com.example.Sahtech.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
@Document(collation = "Nutrisioniste")
public class Nutrisioniste extends Utilisateurs {

    @Id
    private Long id;

    private String Specialite;

}
