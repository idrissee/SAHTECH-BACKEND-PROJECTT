package com.example.Sahtech.entities;


import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.TypeAdditif;
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
    private String idAdditif;

    private String codeAdditif;
    private String nomAdditif;
    private TypeAdditif typeAdditif;
    private Float seuil;


}