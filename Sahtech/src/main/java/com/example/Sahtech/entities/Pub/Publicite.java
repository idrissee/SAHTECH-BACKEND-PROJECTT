package com.example.Sahtech.entities.Pub;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

/**
 * Entité représentant une publicité dans le système SAHTECH
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "Publicite")
public class Publicite {
    @Id
    private String id;
    
    // Relations
    @DBRef
    private Partenaire partenaire;
    private String partenaire_id;
    
    // Informations de base
    private String titre;
    private String description;
    private String imageUrl;
    private String lienRedirection;
    private TypePublicite typePub;
    
    // État et statut
    private StatusPublicite statusPublicite;
    private EtatPublicite etatPublicite;
    
    // Période de validité
    private Date dateDebut;
    private Date dateFin;
    
}
