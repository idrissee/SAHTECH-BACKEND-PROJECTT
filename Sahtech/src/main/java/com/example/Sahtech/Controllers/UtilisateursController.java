package com.example.Sahtech.Controllers;



import com.example.Sahtech.Dto.UtilisateursDto;

import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.repositories.UtilisateursRepository;
import com.example.Sahtech.services.UtilisateurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/API/Sahtech/")
public class UtilisateursController {

    @Autowired
    private UtilisateursRepository utilisateursRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private Mapper<Utilisateurs, UtilisateursDto> utilisateursMapper;

    @GetMapping("/All")
    public List<UtilisateursDto> getAllUsers() {
        List<Utilisateurs> Users = utilisateurService.getAllUtilisateurs();
        return Users.stream()
                .map(utilisateursMapper::mapTo)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Utilisateurs> getUserById(@PathVariable Long id) {
        Utilisateurs utilisateurs = utilisateursRepository.findById(id).get();
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);

    }



    @PostMapping
    public ResponseEntity<Utilisateurs> addUser(@RequestBody Utilisateurs user) {
           Utilisateurs utilisateurs = utilisateursRepository.save(user);
           return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }



    }




