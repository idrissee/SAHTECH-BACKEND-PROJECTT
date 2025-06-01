package com.example.Sahtech.entities.Pub;

import com.example.Sahtech.Enum.StatutPartenaire;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Entité représentant un partenaire publicitaire dans le système SAHTECH
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "Partenaire")
public class Partenaire {
    @Id
    private String id;
    
    // Informations de base
    private String nom;
    private String domaineActivite;
    private String email;
    private String telephone;
    private String siteWeb;
    private String logo;
    
    // Dates et statut
    private Date dateInscription;
    private StatutPartenaire statut;
    
    // Informations financières
    private Double solde;
    
    // Informations supplémentaires
    private String description;
    private String conditions;
    private String referent;
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getDomaineActivite() {
        return domaineActivite;
    }
    
    public void setDomaineActivite(String domaineActivite) {
        this.domaineActivite = domaineActivite;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getSiteWeb() {
        return siteWeb;
    }
    
    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public Date getDateInscription() {
        return dateInscription;
    }
    
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }
    
    public StatutPartenaire getStatut() {
        return statut;
    }
    
    public void setStatut(StatutPartenaire statut) {
        this.statut = statut;
    }
    
    public Double getSolde() {
        return solde;
    }
    
    public void setSolde(Double solde) {
        this.solde = solde;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getConditions() {
        return conditions;
    }
    
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    
    public String getReferent() {
        return referent;
    }
    
    public void setReferent(String referent) {
        this.referent = referent;
    }
} 