package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditifsDto {
    private String idAdditif;
    private String nomAdditif;
    private Float seuil;
    private Maladie maladieCause;
    private List<String> produitsIds;
}