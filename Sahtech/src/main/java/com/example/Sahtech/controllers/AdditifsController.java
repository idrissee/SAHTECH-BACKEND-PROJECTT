package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.AdditifsDto;
import com.example.Sahtech.entities.Additifs;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.AdditifsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AdditifsController {


    private AdditifsService additifsService;

    private Mapper<Additifs, AdditifsDto> additifsMapper;

    public AdditifsController(AdditifsService additifsService, Mapper<Additifs ,AdditifsDto> additifsMapper) {
        this.additifsService = additifsService;
        this.additifsMapper = additifsMapper;
    }

    @PostMapping(path = "additifs")
    public ResponseEntity<AdditifsDto> createAdditifs(@RequestBody AdditifsDto additifsDto){
        Additifs additifs = additifsMapper.mapFrom(additifsDto);
        Additifs additifsaved = additifsService.save(additifs);
        return new ResponseEntity<>(additifsMapper.mapTo(additifsaved), HttpStatus.CREATED);
    }

    @GetMapping(path ="/additifs")
    public List<AdditifsDto> listAdditifs() {
        List<Additifs> additifs  = additifsService.findAll();
        return  additifs.stream()
                .map(additifsMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path ="additifs/{id}")
    public ResponseEntity<AdditifsDto> getAdditif(@PathVariable("id") Long id){
        Optional<Additifs> foundadditif = additifsService.findOnebyId(id);
        return foundadditif.map(additif-> {
           AdditifsDto additifsDto = additifsMapper.mapTo(additif);
            return new ResponseEntity<>(additifsDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "additifs/{id}")
    public ResponseEntity<AdditifsDto> fullUpdateAdditif(
            @PathVariable("id") Long id,
            @RequestBody AdditifsDto additifsDto) {

        if(!additifsService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        additifsDto.setIdAdditif(id);
        Additifs additif = additifsMapper.mapFrom(additifsDto);
        Additifs savedAdditif = additifsService.save(additif);
        return new ResponseEntity<>(
                additifsMapper.mapTo(savedAdditif)
                , HttpStatus.OK);
    }

    @PatchMapping(path ="additifs/{id}")
    public ResponseEntity<AdditifsDto> partialUpdateAdditif(
            @PathVariable("id") Long id,
            @RequestBody AdditifsDto additifDto){

        if(!additifsService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Additifs additif = additifsMapper.mapFrom(additifDto);
        Additifs savedAdditif = additifsService.partialUpdate(id,additif);
        return new ResponseEntity<>(
                additifsMapper.mapTo(savedAdditif)
                , HttpStatus.OK);
    }

    @DeleteMapping(path ="additifs/{id}")
    public ResponseEntity deleteAdditif(@PathVariable("id") Long id){
        additifsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
