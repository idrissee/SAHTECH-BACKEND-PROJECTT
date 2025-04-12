package com.example.Sahtech.entities;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
@Document(collection = "Publicite")
public class Publicite {
    @Id
    private Long id;
    private StatusPublicite statusPublicite;
    private EtatPublicite etatPublicite;
    private String nomEntreprise;
    private Date dateDebut;
    private Date dateFin;

    
}
