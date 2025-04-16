package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Additifs;
import com.example.Sahtech.repositories.AdditifsRepository;
import com.example.Sahtech.services.AdditifsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdditifsServiceImpl implements AdditifsService {

    AdditifsRepository additifsRepository;

    public AdditifsServiceImpl(AdditifsRepository additifsRepository){
        this.additifsRepository = additifsRepository;
    }

    @Override
    public Additifs save(Additifs additifs) {
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

    @Override
    public Optional<Additifs> findOnebyId(String id) {
        return additifsRepository.findById(id);
    }

    @Override
    public boolean isExists(String id) {
        return additifsRepository.existsById(id);
    }

    @Override
    public Additifs partialUpdate(String id, Additifs additif) {
        additif.setIdAdditif(id);

        return additifsRepository.findById(id).map(exisitingAdditif ->{
            Optional.ofNullable(additif.getNomAdditif()).ifPresent(exisitingAdditif::setNomAdditif);
            Optional.ofNullable(additif.getSeuil()).ifPresent(exisitingAdditif::setSeuil);
            return additifsRepository.save(exisitingAdditif);
        }).orElseThrow(() -> new RuntimeException("Additif not found"));
    }

    @Override
    public void delete(String id) {
        additifsRepository.deleteById(id);
    }

    @Override
    public  Optional<Additifs> getByNom(String nom) {
        return additifsRepository.findByNomAdditif(nom);
    }


}
