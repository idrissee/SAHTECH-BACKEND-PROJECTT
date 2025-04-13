package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.NutrisionisteDto;
import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.AuthorizationService;
import com.example.Sahtech.services.NutrisionisteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Nutrisionistes")
public class NutrisionisteController {

    @Autowired
    private NutrisionisteService nutrisionisteService;

    @Autowired
    private Mapper<Nutrisioniste, NutrisionisteDto> nutrisionisteMapper;
    
    @Autowired
    private AuthorizationService authorizationService;

    // GET ALL - réservé à l'admin (déjà géré par SecurityConfig)
    @GetMapping("/All")
    public ResponseEntity<List<NutrisionisteDto>> getAllNutrisionistes() {
        // Double vérification que l'utilisateur est bien un admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        List<Nutrisioniste> nutrisionistes = nutrisionisteService.getAllNutrisionistes();
        List<NutrisionisteDto> dtos = nutrisionistes.stream()
                .map(nutrisionisteMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // GET BY ID - accessible à l'admin et au nutritionniste lui-même
    @GetMapping("/{id}")
    public ResponseEntity<NutrisionisteDto> getNutrisionisteById(@PathVariable String id, HttpServletRequest request) {
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        Nutrisioniste nutrisioniste = nutrisionisteService.getNutrisionisteById(id);
        if (nutrisioniste == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nutrisionisteMapper.mapTo(nutrisioniste), HttpStatus.OK);
    }

    // GET BY EMAIL - réservé à l'admin (déjà géré par SecurityConfig)
    @GetMapping("/email/{email}")
    public NutrisionisteDto getNutrisionisteByEmail(@PathVariable String email) {
        return nutrisionisteMapper.mapTo(nutrisionisteService.getNutrisionisteByEmail(email));
    }

    // GET BY NUMÉRO DE TÉLÉPHONE - réservé à l'admin (déjà géré par SecurityConfig)
    @GetMapping("/telephone/{telephone}")
    public NutrisionisteDto getNutrisionisteByTelephone(@PathVariable String telephone) {
        return nutrisionisteMapper.mapTo(nutrisionisteService.getNutrisionisteByTelephone(telephone));
    }

    // GET BY SPECIALITE - réservé à l'admin (déjà géré par SecurityConfig)
    @GetMapping("/specialite/{specialite}")
    public List<NutrisionisteDto> getNutrisionistesBySpecialite(@PathVariable String specialite) {
        return nutrisionisteService.getNutrisionistesBySpecialite(specialite).stream()
                .map(nutrisionisteMapper::mapTo)
                .collect(Collectors.toList());
    }

    // CREATE - réservé à l'admin (déjà géré par SecurityConfig)
    @PostMapping("/Create")
    public NutrisionisteDto createNutrisioniste(@RequestBody NutrisionisteDto nutrisionisteDto) {
        Nutrisioniste nutrisioniste = nutrisionisteMapper.mapFrom(nutrisionisteDto);
        return nutrisionisteMapper.mapTo(nutrisionisteService.createNutrisioniste(nutrisioniste));
    }

    // UPDATE - accessible à l'admin et au nutritionniste lui-même
    @PutMapping("/Update/{id}")
    public ResponseEntity<NutrisionisteDto> updateNutrisioniste(@PathVariable String id, @RequestBody NutrisionisteDto nutrisionisteDto, HttpServletRequest request) {
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        Nutrisioniste nutrisioniste = nutrisionisteMapper.mapFrom(nutrisionisteDto);
        Nutrisioniste updated = nutrisionisteService.updateNutrisioniste(id, nutrisioniste);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nutrisionisteMapper.mapTo(updated), HttpStatus.OK);
    }

    // DELETE - réservé à l'admin (déjà géré par SecurityConfig)
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteNutrisioniste(@PathVariable String id) {
        boolean deleted = nutrisionisteService.deleteNutrisioniste(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
