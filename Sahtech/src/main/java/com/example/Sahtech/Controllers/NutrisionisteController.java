package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.LocalisationDto;
import com.example.Sahtech.Dto.NutrisionisteDto;
import com.example.Sahtech.entities.Localisation;
import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.LocalisationService;
import com.example.Sahtech.services.NutrisionisteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class NutrisionisteController {
    
    private final NutrisionisteService nutrisionisteService;
    private final LocalisationService localisationService;
    private final Mapper<Nutrisioniste, NutrisionisteDto> nutrisionisteMapper;
    private final Mapper<Localisation, LocalisationDto> localisationMapper;
    
    public NutrisionisteController(
            NutrisionisteService nutrisionisteService,
            LocalisationService localisationService,
            Mapper<Nutrisioniste, NutrisionisteDto> nutrisionisteMapper,
            Mapper<Localisation, LocalisationDto> localisationMapper) {
        this.nutrisionisteService = nutrisionisteService;
        this.localisationService = localisationService;
        this.nutrisionisteMapper = nutrisionisteMapper;
        this.localisationMapper = localisationMapper;
    }
    
    @PostMapping(path = "/nutritionistes")
    public ResponseEntity<NutrisionisteDto> createNutrisioniste(@RequestBody NutrisionisteDto nutrisionisteDto) {
        Nutrisioniste nutrisioniste = nutrisionisteMapper.mapFrom(nutrisionisteDto);
        Nutrisioniste savedNutrisioniste = nutrisionisteService.save(nutrisioniste);
        return new ResponseEntity<>(nutrisionisteMapper.mapTo(savedNutrisioniste), HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/nutritionistes")
    public List<NutrisionisteDto> listNutrisionistes() {
        List<Nutrisioniste> nutrisionistes = nutrisionisteService.findAll();
        return nutrisionistes.stream()
                .map(nutrisionisteMapper::mapTo)
                .collect(Collectors.toList());
    }
    
    @GetMapping(path = "/nutritionistes/{id}")
    public ResponseEntity<NutrisionisteDto> getNutrisioniste(@PathVariable("id") Long id) {
        Optional<Nutrisioniste> foundNutrisioniste = nutrisionisteService.findOneById(id);
        return foundNutrisioniste.map(nutrisioniste -> {
            NutrisionisteDto nutrisionisteDto = nutrisionisteMapper.mapTo(nutrisioniste);
            return new ResponseEntity<>(nutrisionisteDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/nutritionistes/{id}")
    public ResponseEntity<NutrisionisteDto> updateNutrisioniste(
            @PathVariable("id") Long id,
            @RequestBody NutrisionisteDto nutrisionisteDto) {
        
        if (!nutrisionisteService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Nutrisioniste nutrisioniste = nutrisionisteMapper.mapFrom(nutrisionisteDto);
        Nutrisioniste updatedNutrisioniste = nutrisionisteService.update(id, nutrisioniste);
        return new ResponseEntity<>(
                nutrisionisteMapper.mapTo(updatedNutrisioniste),
                HttpStatus.OK);
    }
    
    @DeleteMapping(path = "/nutritionistes/{id}")
    public ResponseEntity<Void> deleteNutrisioniste(@PathVariable("id") Long id) {
        nutrisionisteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // Endpoints pour gérer la relation avec Localisation
    
    @GetMapping(path = "/nutritionistes/{id}/localisation")
    public ResponseEntity<LocalisationDto> getLocalisationForNutrisioniste(@PathVariable("id") Long id) {
        Optional<Nutrisioniste> foundNutrisioniste = nutrisionisteService.findOneById(id);
        
        if (foundNutrisioniste.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Nutrisioniste nutrisioniste = foundNutrisioniste.get();
        
        if (nutrisioniste.getLocalisationId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Optional<Localisation> foundLocalisation = localisationService.findOneById(nutrisioniste.getLocalisationId());
        
        return foundLocalisation.map(localisation -> {
            LocalisationDto localisationDto = localisationMapper.mapTo(localisation);
            return new ResponseEntity<>(localisationDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/nutritionistes/{id}/localisation/{localisationId}")
    public ResponseEntity<NutrisionisteDto> setLocalisationForNutrisioniste(
            @PathVariable("id") Long id,
            @PathVariable("localisationId") Long localisationId) {
        
        // Vérifier que le nutritionniste existe
        if (!nutrisionisteService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Vérifier que la localisation existe
        if (!localisationService.isExists(localisationId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Optional<Nutrisioniste> foundNutrisioniste = nutrisionisteService.findOneById(id);
        
        if (foundNutrisioniste.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Nutrisioniste nutrisioniste = foundNutrisioniste.get();
        nutrisioniste.setLocalisationId(localisationId);
        
        Nutrisioniste updatedNutrisioniste = nutrisionisteService.save(nutrisioniste);
        
        return new ResponseEntity<>(nutrisionisteMapper.mapTo(updatedNutrisioniste), HttpStatus.OK);
    }
    
    @GetMapping(path = "/localisations/{localisationId}/nutritionistes")
    public List<NutrisionisteDto> getNutrisionistesByLocalisation(@PathVariable("localisationId") Long localisationId) {
        List<Nutrisioniste> nutrisionistes = nutrisionisteService.findByLocalisationId(localisationId);
        return nutrisionistes.stream()
                .map(nutrisionisteMapper::mapTo)
                .collect(Collectors.toList());
    }
} 