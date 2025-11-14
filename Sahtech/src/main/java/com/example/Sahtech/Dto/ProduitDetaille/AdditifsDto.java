package com.example.Sahtech.Dto.ProduitDetaille;

import com.example.Sahtech.Enum.TypeAdditif;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdditifsDto {

    private String idAdditif;

    private String codeAdditif;
    private String nomAdditif;
    private TypeAdditif typeAdditif;
    private String seuil;
    private String toxicite;


}