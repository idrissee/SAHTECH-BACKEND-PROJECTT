package com.example.Sahtech.services.Impl.Pub;

import com.example.Sahtech.Enum.StatutPartenaire;
import com.example.Sahtech.entities.Pub.Partenaire;
import com.example.Sahtech.exceptions.FondsInsuffisantsException;
import com.example.Sahtech.repositories.Pub.PartenaireRepository;
import com.example.Sahtech.services.Interfaces.Pub.PartenaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des partenaires
 */
@Service
@RequiredArgsConstructor
public class PartenaireServiceImpl implements PartenaireService {

    private final PartenaireRepository partenaireRepository;

    @Override
    public Partenaire save(Partenaire partenaire) {
        // Initialiser les valeurs par défaut si nécessaire
        if (partenaire.getDateInscription() == null) {
            partenaire.setDateInscription(new Date());
        }
        
        if (partenaire.getStatut() == null) {
            partenaire.setStatut(StatutPartenaire.EN_ATTENTE);
        }
        
        if (partenaire.getSolde() == null) {
            partenaire.setSolde(0.0);
        }
        
        return partenaireRepository.save(partenaire);
    }

    @Override
    public Partenaire update(String id, Partenaire partenaire) {
        Optional<Partenaire> existingPartenaireOpt = partenaireRepository.findById(id);
        
        if (existingPartenaireOpt.isPresent()) {
            Partenaire existingPartenaire = existingPartenaireOpt.get();
            
            // Mettre à jour les champs
            existingPartenaire.setNom(partenaire.getNom());
            existingPartenaire.setDomaineActivite(partenaire.getDomaineActivite());
            existingPartenaire.setEmail(partenaire.getEmail());
            existingPartenaire.setTelephone(partenaire.getTelephone());
            existingPartenaire.setSiteWeb(partenaire.getSiteWeb());
            existingPartenaire.setLogo(partenaire.getLogo());
            existingPartenaire.setDescription(partenaire.getDescription());
            existingPartenaire.setConditions(partenaire.getConditions());
            existingPartenaire.setReferent(partenaire.getReferent());
            
            // Ne pas mettre à jour certains champs sensibles directement
            // comme le statut, la date d'inscription ou le solde
            
            return partenaireRepository.save(existingPartenaire);
        }
        
        // Si le partenaire n'existe pas, on le crée
        return save(partenaire);
    }

    @Override
    public Optional<Partenaire> findById(String id) {
        return partenaireRepository.findById(id);
    }

    @Override
    public Optional<Partenaire> findByEmail(String email) {
        return partenaireRepository.findByEmail(email);
    }

    @Override
    public List<Partenaire> findAll() {
        return partenaireRepository.findAll();
    }

    @Override
    public List<Partenaire> findByDomaineActivite(String domaineActivite) {
        return partenaireRepository.findByDomaineActivite(domaineActivite);
    }

    @Override
    public List<Partenaire> findByStatut(StatutPartenaire statut) {
        return partenaireRepository.findByStatut(statut);
    }

    @Override
    public List<Partenaire> searchByNom(String nom) {
        return partenaireRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public Partenaire approuverPartenaire(String id) {
        Optional<Partenaire> partenaireOpt = partenaireRepository.findById(id);
        
        if (partenaireOpt.isPresent()) {
            Partenaire partenaire = partenaireOpt.get();
            partenaire.setStatut(StatutPartenaire.ACTIF);
            return partenaireRepository.save(partenaire);
        }
        
        return null;
    }

    @Override
    public Partenaire suspendrePartenaire(String id) {
        Optional<Partenaire> partenaireOpt = partenaireRepository.findById(id);
        
        if (partenaireOpt.isPresent()) {
            Partenaire partenaire = partenaireOpt.get();
            partenaire.setStatut(StatutPartenaire.SUSPENDU);
            return partenaireRepository.save(partenaire);
        }
        
        return null;
    }

    @Override
    public Partenaire resilierPartenariat(String id, String motif) {
        Optional<Partenaire> partenaireOpt = partenaireRepository.findById(id);
        
        if (partenaireOpt.isPresent()) {
            Partenaire partenaire = partenaireOpt.get();
            partenaire.setStatut(StatutPartenaire.RESILIE);
            // On pourrait stocker le motif dans un champ supplémentaire
            return partenaireRepository.save(partenaire);
        }
        
        return null;
    }

    @Override
    public Partenaire ajouterFonds(String id, Double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro");
        }
        
        Optional<Partenaire> partenaireOpt = partenaireRepository.findById(id);
        
        if (partenaireOpt.isPresent()) {
            Partenaire partenaire = partenaireOpt.get();
            Double nouveauSolde = partenaire.getSolde() + montant;
            partenaire.setSolde(nouveauSolde);
            return partenaireRepository.save(partenaire);
        }
        
        return null;
    }

    @Override
    public Partenaire debiterFonds(String id, Double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro");
        }
        
        Optional<Partenaire> partenaireOpt = partenaireRepository.findById(id);
        
        if (partenaireOpt.isPresent()) {
            Partenaire partenaire = partenaireOpt.get();
            
            if (partenaire.getSolde() < montant) {
                throw new FondsInsuffisantsException(id, montant, partenaire.getSolde());
            }
            
            Double nouveauSolde = partenaire.getSolde() - montant;
            partenaire.setSolde(nouveauSolde);
            return partenaireRepository.save(partenaire);
        }
        
        return null;
    }

    @Override
    public void delete(String id) {
        partenaireRepository.deleteById(id);
    }
} 