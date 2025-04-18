package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.UtilisateursDto;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.AuthorizationService;
import com.example.Sahtech.services.ImageService;
import com.example.Sahtech.services.Impl.ImageServiceImpl;
import com.example.Sahtech.services.UtilisateursService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Utilisateurs")
public class UtilisateursController {

    @Autowired
    private UtilisateursService utilisateurService;

    @Autowired
    private Mapper<Utilisateurs, UtilisateursDto> utilisateursMapper;
    
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ImageServiceImpl imageServiceImpl;

    // GET ALL USERS - réservé à l'admin (déjà géré par SecurityConfig)
    @GetMapping("/All")
    public ResponseEntity<List<UtilisateursDto>> getAllUsers() {
        // Double vérification que l'utilisateur est bien un admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        List<Utilisateurs> users = utilisateurService.getAllUtilisateurs();
        List<UtilisateursDto> userDtos = users.stream()
                .map(utilisateursMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    // GET USER BY ID - accessible à l'admin et à l'utilisateur lui-même
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateursDto> getUserById(@PathVariable String id, HttpServletRequest request) {
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        Utilisateurs user = utilisateurService.getUtilisateurById(id);
        if (user != null) {
            return new ResponseEntity<>(utilisateursMapper.mapTo(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET USERS BY EMAIL - réservé à l'admin (déjà géré par SecurityConfig)
    @GetMapping("/email")
    public ResponseEntity<UtilisateursDto> getUsersByEmail(@RequestParam String email) {
        Utilisateurs user = utilisateurService.getUtilisateursByEmail(email);
        if (!(user==null)) {
            return new ResponseEntity<>(utilisateursMapper.mapTo(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE NEW USER - réservé à l'admin (déjà géré par SecurityConfig)
    @PostMapping
    public ResponseEntity<UtilisateursDto> addUser(@RequestBody UtilisateursDto userDto) {
        Utilisateurs user = utilisateursMapper.mapFrom(userDto);
        Utilisateurs savedUser = utilisateurService.addUtilisateur(user);
        UtilisateursDto savedDto = utilisateursMapper.mapTo(savedUser);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    // UPDATE USER - accessible à l'admin et à l'utilisateur lui-même
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateursDto> updateUser(@PathVariable String id, @RequestBody UtilisateursDto userDto, HttpServletRequest request) {
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        Utilisateurs updatedUser = utilisateursMapper.mapFrom(userDto);
        updatedUser.setId(id);
        Utilisateurs saved = utilisateurService.updateUtilisateur(id,updatedUser);
        if (saved != null) {
            return new ResponseEntity<>(utilisateursMapper.mapTo(saved), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE USER - réservé à l'admin (déjà géré par SecurityConfig)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        boolean deleted = utilisateurService.deleteUtilisateur(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/uploadPhoto")
    public ResponseEntity<Utilisateurs> uploadPhoto(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        String photoUrl = imageServiceImpl.uploadImage(file);
        Utilisateurs updated = utilisateurService.setPhotoUrl(id, photoUrl);
        return ResponseEntity.ok(updated);
    }
}