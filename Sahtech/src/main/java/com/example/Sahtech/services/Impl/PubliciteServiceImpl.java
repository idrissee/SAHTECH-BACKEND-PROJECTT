package com.example.Sahtech.services.Impl;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import com.example.Sahtech.entities.Partenaire;
import com.example.Sahtech.entities.Publicite;
import com.example.Sahtech.repositories.PartenaireRepository;
import com.example.Sahtech.repositories.PubliciteRepository;
import com.example.Sahtech.services.PubliciteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des publicités
 */
@Service
@RequiredArgsConstructor
public class PubliciteServiceImpl implements PubliciteService {

    private final PubliciteRepository publiciteRepository;
    private final PartenaireRepository partenaireRepository;

    @Override
    public Publicite save(Publicite publicite) {
        // Initialiser les valeurs par défaut si nécessaire
        if (publicite.getStatusPublicite() == null) {
            publicite.setStatusPublicite(StatusPublicite.EN_ATTENTE);
        }
        
        if (publicite.getEtatPublicite() == null) {
            publicite.setEtatPublicite(EtatPublicite.DESACTIVEE);
        }

        
        // Vérifier que le partenaire existe
        if (publicite.getPartenaire_id() != null) {
            Optional<Partenaire> partenaireOpt = partenaireRepository.findById(publicite.getPartenaire_id());
            if (partenaireOpt.isPresent()) {
                publicite.setPartenaire(partenaireOpt.get());
            }
        }
        
        return publiciteRepository.save(publicite);
    }

    @Override
    public Publicite update(String id, Publicite publicite) {
        Optional<Publicite> existingPubliciteOpt = publiciteRepository.findById(id);
        
        if (existingPubliciteOpt.isPresent()) {
            Publicite existingPublicite = existingPubliciteOpt.get();
            
            // Mettre à jour les champs
            existingPublicite.setTitre(publicite.getTitre());
            existingPublicite.setDescription(publicite.getDescription());
            existingPublicite.setImageUrl(publicite.getImageUrl());
            existingPublicite.setLienRedirection(publicite.getLienRedirection());
            existingPublicite.setTypePub(publicite.getTypePub());
            existingPublicite.setDateDebut(publicite.getDateDebut());
            existingPublicite.setDateFin(publicite.getDateFin());
            
            // Ne pas mettre à jour certains champs sensibles directement
            // comme le statut, l'état, les impressions et les clics
            
            return publiciteRepository.save(existingPublicite);
        }
        
        // Si la publicité n'existe pas, on la crée
        return save(publicite);
    }

    @Override
    public Optional<Publicite> findById(String id) {
        return publiciteRepository.findById(id);
    }

    @Override
    public List<Publicite> findAll() {
        return publiciteRepository.findAll();
    }

    @Override
    public List<Publicite> findByStatusPublicite(StatusPublicite statusPublicite) {
        return publiciteRepository.findByStatusPublicite(statusPublicite);
    }

    @Override
    public List<Publicite> findByEtatPublicite(EtatPublicite etatPublicite) {
        return publiciteRepository.findByEtatPublicite(etatPublicite);
    }

    @Override
    public List<Publicite> findByPartenaireId(String partenaireId) {
        return publiciteRepository.findByPartenaire_id(partenaireId);
    }

    @Override
    public List<Publicite> findByTypePub(TypePublicite typePub) {
        return publiciteRepository.findByTypePub(typePub);
    }

    @Override
    public List<Publicite> findActivePublicites() {
        return publiciteRepository.findByStatusPubliciteAndEtatPublicite(
                StatusPublicite.ACCEPTEE, EtatPublicite.PUBLIEE);
    }

    @Override
    public List<Publicite> findCurrentPublicites() {
        Date now = new Date();
        return publiciteRepository.findByDateDebutBeforeAndDateFinAfter(now, now);
    }

    @Override
    public Publicite accepterPublicite(String id) {
        Optional<Publicite> publiciteOpt = publiciteRepository.findById(id);
        
        if (publiciteOpt.isPresent()) {
            Publicite publicite = publiciteOpt.get();
            publicite.setStatusPublicite(StatusPublicite.ACCEPTEE);
            return publiciteRepository.save(publicite);
        }
        
        return null;
    }

    @Override
    public Publicite rejeterPublicite(String id, String motif) {
        Optional<Publicite> publiciteOpt = publiciteRepository.findById(id);
        
        if (publiciteOpt.isPresent()) {
            Publicite publicite = publiciteOpt.get();
            publicite.setStatusPublicite(StatusPublicite.REJECTEE);
            // On pourrait stocker le motif dans un champ supplémentaire
            return publiciteRepository.save(publicite);
        }
        
        return null;
    }

    @Override
    public Publicite activerPublicite(String id) {
        Optional<Publicite> publiciteOpt = publiciteRepository.findById(id);
        
        if (publiciteOpt.isPresent()) {
            Publicite publicite = publiciteOpt.get();
            publicite.setEtatPublicite(EtatPublicite.PUBLIEE);
            return publiciteRepository.save(publicite);
        }
        
        return null;
    }

    @Override
    public Publicite desactiverPublicite(String id) {
        Optional<Publicite> publiciteOpt = publiciteRepository.findById(id);
        
        if (publiciteOpt.isPresent()) {
            Publicite publicite = publiciteOpt.get();
            publicite.setEtatPublicite(EtatPublicite.DESACTIVEE);
            return publiciteRepository.save(publicite);
        }
        
        return null;
    }


    @Override
    public void delete(String id) {
        publiciteRepository.deleteById(id);
    }
} 