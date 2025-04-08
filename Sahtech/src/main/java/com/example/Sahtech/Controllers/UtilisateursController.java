package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.UtilisateursDto;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.repositories.UtilisateursRepository;
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
    private UtilisateursRepository utilisateursRepository;

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
    public ResponseEntity<UtilisateursDto> getUserById(@PathVariable Long id) {
        return utilisateursRepository.findById(id)
                .map(user -> new ResponseEntity<>(utilisateursMapper.mapTo(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
        Utilisateurs savedUser = utilisateursRepository.save(user);
        UtilisateursDto savedDto = utilisateursMapper.mapTo(savedUser);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateursDto> updateUser(@PathVariable Long id, @RequestBody UtilisateursDto userDto) {
        return utilisateursRepository.findById(id).map(existingUser -> {
            Utilisateurs updatedUser = utilisateursMapper.mapFrom(userDto);
            updatedUser.setId(id);
            Utilisateurs saved = utilisateursRepository.save(updatedUser);
            return new ResponseEntity<>(utilisateursMapper.mapTo(saved), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (utilisateursRepository.existsById(id)) {
            utilisateursRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
