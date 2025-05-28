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
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Partenaire getPartenaire() {
        return partenaire;
    }
    
    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }
    
    public String getPartenaire_id() {
        return partenaire_id;
    }
    
    public void setPartenaire_id(String partenaire_id) {
        this.partenaire_id = partenaire_id;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getLienRedirection() {
        return lienRedirection;
    }
    
    public void setLienRedirection(String lienRedirection) {
        this.lienRedirection = lienRedirection;
    }
    
    public TypePublicite getTypePub() {
        return typePub;
    }
    
    public void setTypePub(TypePublicite typePub) {
        this.typePub = typePub;
    }
    
    public StatusPublicite getStatusPublicite() {
        return statusPublicite;
    }
    
    public void setStatusPublicite(StatusPublicite statusPublicite) {
        this.statusPublicite = statusPublicite;
    }
    
    public EtatPublicite getEtatPublicite() {
        return etatPublicite;
    }
    
    public void setEtatPublicite(EtatPublicite etatPublicite) {
        this.etatPublicite = etatPublicite;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public Date getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
