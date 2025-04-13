package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.Enum.TypeAdditif;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdditifsDto {



    private String idAdditif;

    private String codeAdditif;

    private String nomAdditif;

    private Float seuil;

    private Maladie maladieCause;

    private TypeAdditif typeAdditif;


}