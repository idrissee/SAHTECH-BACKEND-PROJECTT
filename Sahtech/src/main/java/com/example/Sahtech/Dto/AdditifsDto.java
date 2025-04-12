package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.Maladie;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class AdditifsDto {


    private Long idAdditif;

    private String nomAdditif;

    private Float seuil;

    private Maladie maladieCause;

    private List<Long> produitsIds;

}