package com.example.Sahtech.Dto;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import lombok.*;


import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
