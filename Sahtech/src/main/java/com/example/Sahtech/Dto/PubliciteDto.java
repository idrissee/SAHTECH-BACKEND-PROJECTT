package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PubliciteDto {
    private String id;
    private StatusPublicite statusPublicite;
    private EtatPublicite etatPublicite;
    private String fabricantId;
    private String nomEntreprise;
    private Date dateDebut;
    private Date dateFin;
    private String titre;
    private String description;
    private String urlImage;
    private String urlRedirection;
    private List<String> publicCible;
    private List<String> produitsPromus;
    private Integer nombreImpressions;
    private Integer nombreClics;
    private Double coutPublicite;
    private Boolean estPaye;
    private String adminApprobateurId;
}
