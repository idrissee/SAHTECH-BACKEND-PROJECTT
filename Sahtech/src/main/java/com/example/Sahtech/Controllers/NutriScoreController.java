package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.IngrediantsDto;
import com.example.Sahtech.Dto.NutriScoreDto;
import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.entities.NutriScore;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.NutriScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class NutriScoreController {

    private final NutriScoreService nutriScoreService;
    private final Mapper<NutriScore, NutriScoreDto> nutriScoreMapper;

    public NutriScoreController(NutriScoreService nutriScoreService, Mapper<NutriScore, NutriScoreDto> nutriScoreMapper) {
        this.nutriScoreService = nutriScoreService;
        this.nutriScoreMapper = nutriScoreMapper;
    }

    @PostMapping(path = "/nutriscores")
    public ResponseEntity<NutriScoreDto> createNutriScore(@RequestBody NutriScoreDto nutriScoreDto) {
        NutriScore nutriScore = nutriScoreMapper.mapFrom(nutriScoreDto);
        NutriScore savedNutriScore = nutriScoreService.save(nutriScore);
        return new ResponseEntity<>(nutriScoreMapper.mapTo(savedNutriScore), HttpStatus.CREATED);
    }

    @GetMapping(path = "/nutriscores")
    public List<NutriScoreDto> listNutriScores() {
        List<NutriScore> nutriScores = nutriScoreService.findAll();
        return nutriScores.stream()
                .map(nutriScoreMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/nutriscores/{id}")
    public ResponseEntity<NutriScoreDto> getNutriScore(@PathVariable("id") Long id) {
        Optional<NutriScore> foundNutriScore = nutriScoreService.findOneById(id);
        return foundNutriScore.map(nutriScore -> {
            NutriScoreDto nutriScoreDto = nutriScoreMapper.mapTo(nutriScore);
            return new ResponseEntity<>(nutriScoreDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping(path = "/nutriscores/{id}")
    public ResponseEntity<NutriScoreDto> fullupdateNutriScore(
            @PathVariable("id") Long id,
            @RequestBody NutriScoreDto nutriScoreDto) {

        if (!nutriScoreService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        NutriScore nutriScore = nutriScoreMapper.mapFrom(nutriScoreDto);
        NutriScore updatedNutriScore = nutriScoreService.update(id, nutriScore);
        return new ResponseEntity<>(
                nutriScoreMapper.mapTo(updatedNutriScore),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/nutriscores/{id}")
    public ResponseEntity<Void> deleteNutriScore(@PathVariable("id") Long id) {
        nutriScoreService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/nutriscores/valeur/{valeur}")
    public List<NutriScoreDto> getNutriScoresByValeur(@PathVariable("valeur") ValeurNutriScore valeur) {
        List<NutriScore> nutriScores = nutriScoreService.findByValeur(valeur);
        return nutriScores.stream()
                .map(nutriScoreMapper::mapTo)
                .collect(Collectors.toList());
    }
} 