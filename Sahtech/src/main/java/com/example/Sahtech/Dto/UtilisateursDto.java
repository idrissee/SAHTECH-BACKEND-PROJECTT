package com.example.Sahtech.Dto;

import com.example.Sahtech.entities.Personne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UtilisateursDto extends Personne {


    private Long id;


    private Date dateDeNaissence;

}
