package com.example.Sahtech.exceptions;

/**
 * Exception levée lorsqu'un partenaire n'a pas assez de fonds pour une opération
 */
public class FondsInsuffisantsException extends RuntimeException {
    
    private final Long partenaireId;
    private final Double montantDemande;
    private final Double soldeActuel;
    
    public FondsInsuffisantsException(Long partenaireId, Double montantDemande, Double soldeActuel) {
        super(String.format("Fonds insuffisants pour le partenaire %d : solde actuel %.2f, montant demandé %.2f", 
                partenaireId, soldeActuel, montantDemande));
        this.partenaireId = partenaireId;
        this.montantDemande = montantDemande;
        this.soldeActuel = soldeActuel;
    }
    
    public Long getPartenaireId() {
        return partenaireId;
    }
    
    public Double getMontantDemande() {
        return montantDemande;
    }
    
    public Double getSoldeActuel() {
        return soldeActuel;
    }
} 