package com.example.Sahtech.Controllers.PubControllers;

import com.example.Sahtech.Dto.Pub.PartenaireDto;
import com.example.Sahtech.Enum.StatutPartenaire;
import com.example.Sahtech.entities.Pub.Partenaire;
import com.example.Sahtech.exceptions.FondsInsuffisantsException;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.Interfaces.Pub.PartenaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Partenaires")
public class PartenaireController {

    @Autowired
    private PartenaireService partenaireService;
    
    @Autowired
    private Mapper<Partenaire, PartenaireDto> partenaireMapper;

    @PostMapping
    public ResponseEntity<PartenaireDto> createPartenaire(@RequestBody PartenaireDto partenaireDto) {
        Partenaire partenaire = partenaireMapper.mapFrom(partenaireDto);
        Partenaire savedPartenaire = partenaireService.save(partenaire);
        return new ResponseEntity<>(partenaireMapper.mapTo(savedPartenaire), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartenaireDto> updatePartenaire(
            @PathVariable String id,
            @RequestBody PartenaireDto partenaireDto) {
        Partenaire partenaire = partenaireMapper.mapFrom(partenaireDto);
        Partenaire updatedPartenaire = partenaireService.update(id, partenaire);
        return new ResponseEntity<>(partenaireMapper.mapTo(updatedPartenaire), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartenaireDto> getPartenaire(@PathVariable String id) {
        Optional<Partenaire> partenaire = partenaireService.findById(id);
        return partenaire.map(p -> new ResponseEntity<>(partenaireMapper.mapTo(p), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<PartenaireDto> getAllPartenaires() {
        return partenaireService.findAll().stream()
                .map(partenaireMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PartenaireDto> getPartenaireByEmail(@PathVariable String email) {
        Optional<Partenaire> partenaire = partenaireService.findByEmail(email);
        return partenaire.map(p -> new ResponseEntity<>(partenaireMapper.mapTo(p), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/domaine/{domaineActivite}")
    public List<PartenaireDto> getPartenairesByDomaine(@PathVariable String domaineActivite) {
        return partenaireService.findByDomaineActivite(domaineActivite).stream()
                .map(partenaireMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/statut/{statut}")
    public List<PartenaireDto> getPartenairesByStatut(@PathVariable StatutPartenaire statut) {
        return partenaireService.findByStatut(statut).stream()
                .map(partenaireMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<PartenaireDto> searchPartenaires(@RequestParam String nom) {
        return partenaireService.searchByNom(nom).stream()
                .map(partenaireMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/approuver")
    public ResponseEntity<PartenaireDto> approuverPartenaire(@PathVariable String id) {
        Partenaire partenaire = partenaireService.approuverPartenaire(id);
        if (partenaire != null) {
            return new ResponseEntity<>(partenaireMapper.mapTo(partenaire), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/suspendre")
    public ResponseEntity<PartenaireDto> suspendrePartenaire(@PathVariable String id) {
        Partenaire partenaire = partenaireService.suspendrePartenaire(id);
        if (partenaire != null) {
            return new ResponseEntity<>(partenaireMapper.mapTo(partenaire), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/resilier")
    public ResponseEntity<PartenaireDto> resilierPartenariat(
            @PathVariable String id,
            @RequestParam String motif) {
        Partenaire partenaire = partenaireService.resilierPartenariat(id, motif);
        if (partenaire != null) {
            return new ResponseEntity<>(partenaireMapper.mapTo(partenaire), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/ajouterFonds")
    public ResponseEntity<?> ajouterFonds(
            @PathVariable String id,
            @RequestParam Double montant) {
        try {
            Partenaire partenaire = partenaireService.ajouterFonds(id, montant);
            if (partenaire != null) {
                return new ResponseEntity<>(partenaireMapper.mapTo(partenaire), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/debiterFonds")
    public ResponseEntity<?> debiterFonds(
            @PathVariable String id,
            @RequestParam Double montant) {
        try {
            Partenaire partenaire = partenaireService.debiterFonds(id, montant);
            if (partenaire != null) {
                return new ResponseEntity<>(partenaireMapper.mapTo(partenaire), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (FondsInsuffisantsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartenaire(@PathVariable String id) {
        partenaireService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
} 