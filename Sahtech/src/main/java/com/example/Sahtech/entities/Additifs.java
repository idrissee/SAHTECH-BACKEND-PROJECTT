package com.example.Sahtech.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "additifs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Additifs {

    @Id
    private Long idAdditif;

    private String nomAdditif;

    private Float seuil;

    private Maladie maladieCause;

    private List<Long> produitsIds;


}