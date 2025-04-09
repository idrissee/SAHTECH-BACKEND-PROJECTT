package com.example.Sahtech.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdditifsDto {


    private Long idAdditif;

    private String nomAdditif;

    private Float seuil;

    private Maladie maladieCause;

    private List<Long> produitsIds;
}