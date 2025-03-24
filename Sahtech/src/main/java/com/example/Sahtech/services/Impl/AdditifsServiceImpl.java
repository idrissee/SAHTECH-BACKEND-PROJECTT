package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Additifs;
import com.example.Sahtech.repositories.AdditifsRepository;
import com.example.Sahtech.services.AdditifsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdditifsServiceImpl implements AdditifsService {

    AdditifsRepository additifsRepository;

    public AdditifsServiceImpl(AdditifsRepository additifsRepository){
        this.additifsRepository = additifsRepository;
    }

    @Override
    public Additifs createAdditifs(Additifs additifs) {
      return additifsRepository.save(additifs);
    }

    @Override
    public List<Additifs> findAll() {
        return StreamSupport.stream(additifsRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
}
