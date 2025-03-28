package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PubliciteDto {
    private Long id;
    private StatusPublicite statusPublicite;
    private EtatPublicite etatPublicite;
    private String nomEntreprise;
    private Date dateDebut;
    private Date dateFin;


}
