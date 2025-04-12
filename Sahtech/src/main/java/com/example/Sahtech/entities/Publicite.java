package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "publicites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publicite {
    @Id
    private String id;
    private StatusPublicite statusPublicite;
    private EtatPublicite etatPublicite;
    private String nomEntreprise;
    private Date dateDebut;
    private Date dateFin;
}
