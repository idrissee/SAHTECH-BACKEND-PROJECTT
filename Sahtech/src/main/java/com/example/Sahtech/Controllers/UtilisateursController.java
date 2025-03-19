package com.example.Sahtech.Controllers;


import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/API/Sahtech/")
public class UtilisateursController {

    @Autowired
    private UtilisateursRepository utilisateursRepository;


    @GetMapping("/All")
    public ResponseEntity<List<Utilisateurs>> getAllUsers() {
        List<Utilisateurs> list = utilisateursRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);

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




