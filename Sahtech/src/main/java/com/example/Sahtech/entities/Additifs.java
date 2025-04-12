package com.example.Sahtech.entities;


import com.example.Sahtech.Enum.Maladie;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "additifs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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