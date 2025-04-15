package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.PubliciteDto;
import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import com.example.Sahtech.entities.Publicite;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.PubliciteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Publicites")
@RequiredArgsConstructor
public class PubliciteController {

    private final PubliciteService publiciteService;
    private final Mapper<Publicite, PubliciteDto> publiciteMapper;

    @PostMapping
    public ResponseEntity<PubliciteDto> createPublicite(@RequestBody PubliciteDto publiciteDto) {
        Publicite publicite = publiciteMapper.mapFrom(publiciteDto);
        Publicite savedPublicite = publiciteService.save(publicite);
        return new ResponseEntity<>(publiciteMapper.mapTo(savedPublicite), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PubliciteDto> updatePublicite(
            @PathVariable String id,
            @RequestBody PubliciteDto publiciteDto) {
        Publicite publicite = publiciteMapper.mapFrom(publiciteDto);
        Publicite updatedPublicite = publiciteService.update(id, publicite);
        return new ResponseEntity<>(publiciteMapper.mapTo(updatedPublicite), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PubliciteDto> getPublicite(@PathVariable String id) {
        Optional<Publicite> publicite = publiciteService.findById(id);
        return publicite.map(p -> new ResponseEntity<>(publiciteMapper.mapTo(p), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<PubliciteDto> getAllPublicites() {
        return publiciteService.findAll().stream()
                .map(publiciteMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/status/{status}")
    public List<PubliciteDto> getPublicitesByStatus(@PathVariable StatusPublicite status) {
        return publiciteService.findByStatusPublicite(status).stream()
                .map(publiciteMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/etat/{etat}")
    public List<PubliciteDto> getPublicitesByEtat(@PathVariable EtatPublicite etat) {
        return publiciteService.findByEtatPublicite(etat).stream()
                .map(publiciteMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/partenaire/{partenaireId}")
    public List<PubliciteDto> getPublicitesByPartenaire(@PathVariable String partenaireId) {
        return publiciteService.findByPartenaireId(partenaireId).stream()
                .map(publiciteMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/type/{type}")
    public List<PubliciteDto> getPublicitesByType(@PathVariable TypePublicite type) {
        return publiciteService.findByTypePub(type).stream()
                .map(publiciteMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/actives")
    public List<PubliciteDto> getActivePublicites() {
        return publiciteService.findActivePublicites().stream()
                .map(publiciteMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/courantes")
    public List<PubliciteDto> getCurrentPublicites() {
        return publiciteService.findCurrentPublicites().stream()
                .map(publiciteMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/accepter")
    public ResponseEntity<PubliciteDto> accepterPublicite(@PathVariable String id) {
        Publicite publicite = publiciteService.accepterPublicite(id);
        if (publicite != null) {
            return new ResponseEntity<>(publiciteMapper.mapTo(publicite), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/rejeter")
    public ResponseEntity<PubliciteDto> rejeterPublicite(
            @PathVariable String id,
            @RequestParam String motif) {
        Publicite publicite = publiciteService.rejeterPublicite(id, motif);
        if (publicite != null) {
            return new ResponseEntity<>(publiciteMapper.mapTo(publicite), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/activer")
    public ResponseEntity<PubliciteDto> activerPublicite(@PathVariable String id) {
        Publicite publicite = publiciteService.activerPublicite(id);
        if (publicite != null) {
            return new ResponseEntity<>(publiciteMapper.mapTo(publicite), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/desactiver")
    public ResponseEntity<PubliciteDto> desactiverPublicite(@PathVariable String id) {
        Publicite publicite = publiciteService.desactiverPublicite(id);
        if (publicite != null) {
            return new ResponseEntity<>(publiciteMapper.mapTo(publicite), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicite(@PathVariable String id) {
        publiciteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
} 