package com.example.Sahtech.Controllers.UsersControllers.NutritionisteDetaille;

import com.example.Sahtech.Dto.Users.NutritionisteDetaille.LocalisationDto;
import com.example.Sahtech.entities.Users.NutritionisteDetaille.Localisation;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.Interfaces.Auth_Author.AuthorizationService;
import com.example.Sahtech.services.Interfaces.Users.NutritionisteDetaille.LocalisationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Localisations")
public class LocalisationController {

    @Autowired
    private AuthorizationService authorizationService;


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
    public ResponseEntity<List<LocalisationDto>> listLocalisations() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Localisation> localisations = localisationService.findAll();
        List<LocalisationDto> localisationDtos = localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());
             return new ResponseEntity<>(localisationDtos, HttpStatus.OK);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<LocalisationDto> getLocalisation(@PathVariable("id") String id, HttpServletRequest request) {

        // Vérifier si l'utilisateur est autorisé (admin ou nutritionniste associé à cette localisation)
        if (!authorizationService.isNutritionisteAuthorizedForLocation(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


        Optional<Localisation> foundLocalisation = localisationService.findOneById(id);
        return foundLocalisation.map(localisation -> {
            LocalisationDto localisationDto = localisationMapper.mapTo(localisation);
            return new ResponseEntity<>(localisationDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity<LocalisationDto> updateLocalisation(
            @PathVariable("id") String id,
            @RequestBody LocalisationDto localisationDto,
            HttpServletRequest request) {

        
        if (!localisationService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Vérifier si l'utilisateur est autorisé (admin ou nutritionniste associé à cette localisation)
        if (!authorizationService.isNutritionisteAuthorizedForLocation(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
    

    
    @GetMapping(path = "/region")
    public ResponseEntity<List<LocalisationDto>> getLocalisationsByRegion(@RequestParam("region") String region) {
        List<Localisation> localisations = localisationService.findByRegion(region);
        List<LocalisationDto> localisationDto =  localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(localisationDto, HttpStatus.OK);
    }
    
    @GetMapping(path = "/ville")
    public ResponseEntity<List<LocalisationDto>> getLocalisationsByVille(@RequestParam("ville") String ville) {
        List<Localisation> localisations = localisationService.findByVille(ville);
        List<LocalisationDto> localisationDtos =  localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());

        return new ResponseEntity<>(localisationDtos, HttpStatus.OK);
    }
    
    @GetMapping(path = "/codePostal")
    public ResponseEntity<List<LocalisationDto>> getLocalisationsByCodePostal(@RequestParam("codePostal") String codePostal) {
        List<Localisation> localisations = localisationService.findByCodePostal(codePostal);
        List<LocalisationDto> localisationDtos = localisations.stream()
                .map(localisationMapper::mapTo)
                .collect(Collectors.toList());

        return new ResponseEntity<>(localisationDtos, HttpStatus.OK);
    }

} 