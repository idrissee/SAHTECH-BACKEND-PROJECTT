package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.NutrisionisteDto;
import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.NutrisionisteService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // GET ALL
    @GetMapping("/All")
    public List<NutrisionisteDto> getAllNutrisionistes() {
        List<Nutrisioniste> nutrisionistes = nutrisionisteService.getAllNutrisionistes();
        return nutrisionistes.stream()
                .map(nutrisionisteMapper::mapTo)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public NutrisionisteDto getNutrisionisteById(@PathVariable String id) {
        return nutrisionisteMapper.mapTo(nutrisionisteService.getNutrisionisteById(id));
    }

    // GET BY EMAIL
    @GetMapping("/email/{email}")
    public NutrisionisteDto getNutrisionisteByEmail(@PathVariable String email) {
        return nutrisionisteMapper.mapTo(nutrisionisteService.getNutrisionisteByEmail(email));
    }

    // GET BY NUMÉRO DE TÉLÉPHONE
    @GetMapping("/telephone/{telephone}")
    public NutrisionisteDto getNutrisionisteByTelephone(@PathVariable String telephone) {
        return nutrisionisteMapper.mapTo(nutrisionisteService.getNutrisionisteByTelephone(telephone));
    }

    // CREATE
    @PostMapping("/Create")
    public NutrisionisteDto createNutrisioniste(@RequestBody NutrisionisteDto nutrisionisteDto) {
        Nutrisioniste nutrisioniste = nutrisionisteMapper.mapFrom(nutrisionisteDto);
        return nutrisionisteMapper.mapTo(nutrisionisteService.createNutrisioniste(nutrisioniste));
    }

    // UPDATE
    @PutMapping("/Update/{id}")
    public NutrisionisteDto updateNutrisioniste(@PathVariable String id, @RequestBody NutrisionisteDto nutrisionisteDto) {
        Nutrisioniste nutrisioniste = nutrisionisteMapper.mapFrom(nutrisionisteDto);
        return nutrisionisteMapper.mapTo(nutrisionisteService.updateNutrisioniste(id, nutrisioniste));
    }

    // DELETE
    @DeleteMapping("/Delete/{id}")
    public void deleteNutrisioniste(@PathVariable String id) {
        nutrisionisteService.deleteNutrisioniste(id);
    }
}
