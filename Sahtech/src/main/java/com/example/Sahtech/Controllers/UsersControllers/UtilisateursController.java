package com.example.Sahtech.Controllers.UsersControllers;

import com.example.Sahtech.Dto.Users.UtilisateursDto;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.Interfaces.Auth_Author.AuthorizationService;
import com.example.Sahtech.services.Impl.Image.ImageServiceImpl;
import com.example.Sahtech.services.Interfaces.Users.UtilisateursService;
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
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/API/Sahtech/Utilisateurs")
public class UtilisateursController {
    
    private static final Logger logger = LoggerFactory.getLogger(UtilisateursController.class);

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
        logger.info("GET request received for user with ID: {}", id);
        
        // Extract the JWT token for debugging
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.error("No valid Authorization header found in request");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            logger.error("User not authorized to access resource with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        Utilisateurs user = utilisateurService.getUtilisateurById(id);
        if (user != null) {
            logger.info("User found: {}", user.getEmail());
            UtilisateursDto userDto = utilisateursMapper.mapTo(user);
            logger.info("User DTO created successfully with name: {} {}", userDto.getPrenom(), userDto.getNom());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            logger.error("User with ID {} not found", id);
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
        
        // Get the existing user first to preserve any fields not included in the update
        Utilisateurs existingUser = utilisateurService.getUtilisateurById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Map the DTO to entity
        Utilisateurs updatedUser = utilisateursMapper.mapFrom(userDto);
        updatedUser.setId(id);
        
        // Preserve the photoUrl if it's not included in the update
        if (updatedUser.getPhotoUrl() == null && existingUser.getPhotoUrl() != null) {
            updatedUser.setPhotoUrl(existingUser.getPhotoUrl());
        }
        
        // Update the user
        Utilisateurs saved = utilisateurService.updateUtilisateur(id, updatedUser);
        if (saved != null) {
            // Log the photoUrl for debugging
            logger.info("User updated with photoUrl: {}", saved.getPhotoUrl());
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

    @GetMapping("/{id}/scanCount")
    public ResponseEntity<Long> getUserScanCount(@PathVariable String id, HttpServletRequest request) {
        // Vérifier si l'utilisateur est autorisé (admin ou lui-même)
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Utilisateurs utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(utilisateur.getCountScans(), HttpStatus.OK);
    }

    @PostMapping("/{id}/uploadPhoto")
    public ResponseEntity<?> uploadPhoto(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            logger.error("User not authorized to upload photo for user ID: {}", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            logger.info("Starting photo upload for user ID: {}", id);
            logger.info("File name: {}, size: {} bytes, content type: {}", 
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType());
            
            // Upload to Cloudinary
            String photoUrl = imageServiceImpl.uploadImage(file);
            logger.info("Cloudinary upload successful. URL: {}", photoUrl);
            
            // Get current user state
            Utilisateurs existingUser = utilisateurService.getUtilisateurById(id);
            logger.info("Current user photoUrl before update: {}", existingUser.getPhotoUrl());
            
            // Update user with new photo URL
            Utilisateurs updated = utilisateurService.setPhotoUrl(id, photoUrl);
            logger.info("User updated. New photoUrl: {}", updated.getPhotoUrl());
            
            if (updated != null) {
                // Log success and the URL
                logger.info("Photo successfully saved to user document in MongoDB");
                
                // Create a response with just the photoUrl for clarity
                Map<String, Object> response = new HashMap<>();
                response.put("photoUrl", photoUrl);
                response.put("message", "Photo uploaded successfully");
                
                logger.info("Sending response: {}", response);
                return ResponseEntity.ok(response);
            } else {
                logger.error("User with ID {} not found after update", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error uploading photo: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload photo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Direct method to set photoUrl - for debugging and ensuring direct field update
    @PutMapping("/{id}/setPhotoUrlDirect")
    public ResponseEntity<?> setPhotoUrlDirect(
            @PathVariable String id,
            @RequestBody Map<String, String> requestBody,
            HttpServletRequest request
    ) {
        // Verify authorization
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            logger.error("User not authorized to set photo URL for user ID: {}", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            // Extract photoUrl from request body
            String photoUrl = requestBody.get("photoUrl");
            if (photoUrl == null || photoUrl.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "photoUrl is required"));
            }
            
            logger.info("Setting photoUrl directly for user ID: {}", id);
            logger.info("PhotoUrl to set: {}", photoUrl);
            
            // Get current user
            Utilisateurs utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Log current state
            logger.info("Current photoUrl: {}", utilisateur.getPhotoUrl());
            
            // Set photoUrl directly
            utilisateur.setPhotoUrl(photoUrl);
            
            // Save user
            Utilisateurs savedUser = utilisateurService.updateUtilisateur(id, utilisateur);
            
            // Log result
            logger.info("User saved with photoUrl: {}", savedUser.getPhotoUrl());
            
            // Return response
            Map<String, Object> response = new HashMap<>();
            response.put("photoUrl", savedUser.getPhotoUrl());
            response.put("message", "PhotoUrl set successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error setting photoUrl directly: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to set photoUrl: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/changePassword")
    public ResponseEntity<?> changePassword(
            @PathVariable String id,
            @RequestBody Map<String, String> requestBody,
            HttpServletRequest request
    ) {
        // Check if user is authorized
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            logger.error("User not authorized to change password for user ID: {}", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        String currentPassword = requestBody.get("currentPassword");
        String newPassword = requestBody.get("newPassword");

        if (currentPassword == null || newPassword == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Current password and new password are required"));
        }

        try {
            boolean passwordChanged = utilisateurService.changePassword(id, currentPassword, newPassword);
            
            if (passwordChanged) {
                return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Current password is incorrect"));
            }
        } catch (Exception e) {
            logger.error("Error changing password: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error changing password: " + e.getMessage()));
        }
    }
}