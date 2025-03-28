package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.AdditifsDto;
import com.example.Sahtech.entities.Additifs;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.AdditifsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdditifsController {

    private AdditifsService additifsService;

    private Mapper<Additifs, AdditifsDto> additifsMapper;

    public AdditifsController(AdditifsService additifsService, Mapper<Additifs ,AdditifsDto> additifsMapper) {
        this.additifsService = additifsService;
        this.additifsMapper = additifsMapper;
    }

    @PostMapping(path = "additifs")
    public AdditifsDto createAdditifs(@RequestBody AdditifsDto additifsDto){
        Additifs additifs = additifsMapper.mapFrom(additifsDto);
        Additifs additifsaved = additifsService.createAdditifs(additifs);
        return additifsMapper.mapTo(additifsaved);
    }
}
