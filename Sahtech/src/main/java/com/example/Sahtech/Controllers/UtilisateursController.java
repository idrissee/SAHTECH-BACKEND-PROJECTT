package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.UtilisateursDto;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Utilisateurs")
public class UtilisateursController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private Mapper<Utilisateurs, UtilisateursDto> utilisateursMapper;

    // GET ALL USERS
    @GetMapping("/All")
    public List<UtilisateursDto> getAllUsers() {
        List<Utilisateurs> users = utilisateurService.getAllUtilisateurs();
        return users.stream()
                .map(utilisateursMapper::mapTo)
                .collect(Collectors.toList());
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateursDto> getUserById(@PathVariable String id) {
        Utilisateurs user = utilisateurService.getUtilisateurById(id);
        if (user != null) {
            return new ResponseEntity<>(utilisateursMapper.mapTo(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET USERS BY EMAIL
    @GetMapping("/email")
    public ResponseEntity<List<UtilisateursDto>> getUsersByEmail(@RequestParam String email) {
        List<Utilisateurs> users = utilisateurService.getUtilisateursByEmail(email);
        List<UtilisateursDto> dtos = users.stream()
                .map(utilisateursMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // CREATE NEW USER
    @PostMapping
    public ResponseEntity<UtilisateursDto> addUser(@RequestBody UtilisateursDto userDto) {
        Utilisateurs user = utilisateursMapper.mapFrom(userDto);
        Utilisateurs savedUser = utilisateurService.addUtilisateur(user);
        UtilisateursDto savedDto = utilisateursMapper.mapTo(savedUser);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateursDto> updateUser(@PathVariable String id, @RequestBody UtilisateursDto userDto) {
        Utilisateurs updatedUser = utilisateursMapper.mapFrom(userDto);
        updatedUser.setId(id);
        Utilisateurs saved = utilisateurService.updateUtilisateur(updatedUser);
        if (saved != null) {
            return new ResponseEntity<>(utilisateursMapper.mapTo(saved), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        boolean deleted = utilisateurService.deleteUtilisateur(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}