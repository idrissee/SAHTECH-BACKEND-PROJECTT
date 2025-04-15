package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.LocalisationDto;
import com.example.Sahtech.entities.Localisation;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.LocalisationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Localisations")
public class LocalisationController {
    
    private final LocalisationService localisationService;
    private final Mapper<Localisation, LocalisationDto> localisationMapper;
    
    public LocalisationController(LocalisationService localisationService, Mapper<Localisation, LocalisationDto> localisationMapper) {
        this.localisationService = localisationService;
        this.localisationMapper = localisationMapper;
    }
    
    @PostMapping()
    public ResponseEntity<LocalisationDto> createLocalisation(@RequestBody LocalisationDto localisationDto) {
        Localisation localisation = localisationMapper.mapFrom(localisationDto);
        Localisation savedLocalisation = localisationService.save(localisation);
        return new ResponseEntity<>(localisationMapper.mapTo(savedLocalisation), HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/All")
    public List<LocalisationDto> listLocalisations() {
        List<Localisation> localisations = localisationService.findAll();
        return localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<LocalisationDto> getLocalisation(@PathVariable("id") String id) {
        Optional<Localisation> foundLocalisation = localisationService.findOneById(id);
        return foundLocalisation.map(localisation -> {
            LocalisationDto localisationDto = localisationMapper.mapTo(localisation);
            return new ResponseEntity<>(localisationDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity<LocalisationDto> updateLocalisation(
            @PathVariable("id") String id,
            @RequestBody LocalisationDto localisationDto) {
        
        if (!localisationService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Localisation localisation = localisationMapper.mapFrom(localisationDto);
        Localisation updatedLocalisation = localisationService.update(id, localisation);
        return new ResponseEntity<>(
                localisationMapper.mapTo(updatedLocalisation),
                HttpStatus.OK);
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteLocalisation(@PathVariable("id") String id) {
        localisationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping(path = "/pays/{pays}")
    public List<LocalisationDto> getLocalisationsByPays(@PathVariable("pays") String pays) {
        List<Localisation> localisations = localisationService.findByPays(pays);
        return localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());
    }
    
    @GetMapping(path = "/region/{region}")
    public List<LocalisationDto> getLocalisationsByRegion(@PathVariable("region") String region) {
        List<Localisation> localisations = localisationService.findByRegion(region);
        return localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());
    }
    
    @GetMapping(path = "/ville/{ville}")
    public List<LocalisationDto> getLocalisationsByVille(@PathVariable("ville") String ville) {
        List<Localisation> localisations = localisationService.findByVille(ville);
        return localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());
    }
    
    @GetMapping(path = "/codePostal/{codePostal}")
    public List<LocalisationDto> getLocalisationsByCodePostal(@PathVariable("codePostal") String codePostal) {
        List<Localisation> localisations = localisationService.findByCodePostal(codePostal);
        return localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());
    }
} 