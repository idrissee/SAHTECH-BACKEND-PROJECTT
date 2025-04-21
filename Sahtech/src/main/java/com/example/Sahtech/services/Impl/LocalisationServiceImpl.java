package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Localisation;
import com.example.Sahtech.repositories.LocalisationRepository;
import com.example.Sahtech.services.LocalisationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LocalisationServiceImpl implements LocalisationService {
    
    private final LocalisationRepository localisationRepository;
    
    public LocalisationServiceImpl(LocalisationRepository localisationRepository) {
        this.localisationRepository = localisationRepository;
    }

    
    @Override
    public List<Localisation> findAll() {
        return StreamSupport.stream(localisationRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Localisation> findOneById(String id) {
        return localisationRepository.findById(id);
    }
    
    @Override
    public boolean isExists(String id) {
        return localisationRepository.existsById(id);
    }
    
    @Override
    public Localisation save(Localisation localisation) {
        return localisationRepository.save(localisation);
    }
    
    @Override
    public Localisation update(String id, Localisation localisation) {
        if (!localisationRepository.existsById(id)) {
            return null;
        }
        
        localisation.setId(id);
        return localisationRepository.save(localisation);
    }
    
    @Override
    public void delete(String id) {
        localisationRepository.deleteById(id);
    }

    
    @Override
    public List<Localisation> findByRegion(String region) {
        return localisationRepository.findByRegion(region);
    }
    
    @Override
    public List<Localisation> findByVille(String ville) {
        return localisationRepository.findByVille(ville);
    }

    @Override
    public List<Localisation> findByCodePostal(String codePostal) {
        Optional<Localisation> localisation = localisationRepository.findByCodePostal(codePostal);
        return localisation.map(List::of).orElse(List.of());
    }
    
    @Override
    public List<Localisation> findAllByCountry(String country) {
        return localisationRepository.findByPays(country);
    }
    
    @Override
    public List<Localisation> findAllByRegion(String region) {
        return localisationRepository.findByRegion(region);
    }
    
    @Override
    public List<Localisation> findAllByCity(String city) {
        return localisationRepository.findByVille(city);
    }
    
    @Override
    public List<Localisation> findAllByPostalCode(String postalCode) {
        return localisationRepository.findByCodePostal(postalCode).map(List::of).orElse(List.of());
    }

} 