package com.example.Sahtech.Controllers.UsersControllers;

import com.example.Sahtech.Dto.Users.NutritionisteDetaille.NutrisionisteDto;
import com.example.Sahtech.Dto.Users.UtilisateursDto;
import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.repositories.Users.NutritionisteDetaille.NutritionisteRepository;
import com.example.Sahtech.services.Impl.Image.ImageServiceImpl;
import com.example.Sahtech.services.Interfaces.Auth_Author.AuthorizationService;
import com.example.Sahtech.services.Interfaces.Users.UtilisateursService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.slf4j.Logger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Utilisateurs")
public class UtilisateursController {

    @Autowired
    private UtilisateursService utilisateurService;

    @Autowired
    private Mapper<Utilisateurs, UtilisateursDto> utilisateursMapper;

    @Autowired
    private Mapper<Nutrisioniste, NutrisionisteDto> nutritionistMapper;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ImageServiceImpl imageServiceImpl;

    @Autowired
    private NutritionisteRepository nutritionistRepository;

    private static final Logger logger = LoggerFactory.getLogger(UtilisateursController.class);


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
        logger.info("Création d'un nouvel utilisateur avec email: {}", userDto.getEmail());

        Utilisateurs user = utilisateursMapper.mapFrom(userDto);

        // Le calcul de l'IMC est géré automatiquement dans le service
        Utilisateurs savedUser = utilisateurService.addUtilisateur(user);

        // Vérifier si l'IMC a bien été calculé
        if (savedUser.getImc() != null) {
            logger.info("IMC calculé pour l'utilisateur {}: {}", savedUser.getId(), savedUser.getImc());
        } else {
            logger.warn("IMC non calculé pour l'utilisateur {}", savedUser.getId());
        }

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
    public ResponseEntity<Utilisateurs> uploadPhoto(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        String photoUrl = imageServiceImpl.uploadImage(file, "utilisateurs/photos");
        Utilisateurs updated = utilisateurService.setPhotoUrl(id, photoUrl);
        return ResponseEntity.ok(updated);
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

    @GetMapping(value = "/{id}/imc")
    public ResponseEntity<?> getImcUser(@PathVariable String id, HttpServletRequest request) {
        // Log pour déboguer
        logger.info("Endpoint GET IMC appelé pour l'utilisateur avec ID: {}", id);

        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            logger.error("User not authorized to access IMC for user ID: {}", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Récupérer l'utilisateur directement
        Utilisateurs utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            logger.error("Utilisateur avec ID {} non trouvé", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Utilisateur non trouvé"));
        }

        // Récupérer l'IMC directement depuis l'entité utilisateur
        Float imc = utilisateur.getImc();
        String interpretation = utilisateur.getInterpretationIMC();

        logger.info("Utilisateur trouvé - Email: {}, Poids: {}, Taille: {}, IMC stocké: {}, Interprétation: {}",
                utilisateur.getEmail(), utilisateur.getPoids(), utilisateur.getTaille(), imc, interpretation);

        // Si l'IMC n'est pas encore calculé mais que les données sont disponibles, le calculer maintenant
        if (imc == null && utilisateur.getPoids() != null && utilisateur.getTaille() != null && utilisateur.getTaille() > 0) {
            // Calculer l'IMC manuellement
            imc = utilisateur.getPoids() / ((utilisateur.getTaille()/100) * (utilisateur.getTaille()/100));

            // Mettre à jour l'utilisateur avec l'IMC calculé
            utilisateur.setImc(imc);

            // Mettre à jour l'interprétation de l'IMC
            utilisateur.updateInterpretationIMC();
            interpretation = utilisateur.getInterpretationIMC();

            utilisateurService.updateUtilisateur(id, utilisateur);

            logger.info("IMC calculé à la volée et stocké: {}, Interprétation: {}", imc, interpretation);
        }

        if (imc == null) {
            logger.warn("IMC non disponible pour l'utilisateur {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "IMC non disponible pour cet utilisateur"));
        }

        logger.info("IMC retourné avec succès: {}, Interprétation: {}", imc, interpretation);
        // Ne retourner que l'IMC sans l'interprétation
        return ResponseEntity.ok(Map.of("imc", imc));
    }

    // Endpoint de test simple pour vérifier le fonctionnement du contrôleur
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        logger.info("Test endpoint called");
        return ResponseEntity.ok("Le contrôleur fonctionne correctement");
    }

    // Endpoint pour forcer le calcul et la mise à jour de l'IMC
    @PostMapping("/{id}/calculer-imc")
    public ResponseEntity<?> calculerEtMajImc(@PathVariable String id, HttpServletRequest request) {
        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            logger.error("User not authorized to update IMC for user ID: {}", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Récupérer l'utilisateur
        Utilisateurs utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Utilisateur non trouvé"));
        }

        // Vérifier si les données nécessaires sont présentes
        if (utilisateur.getPoids() == null || utilisateur.getTaille() == null || utilisateur.getTaille() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Données de poids ou taille manquantes ou invalides"));
        }

        // Calculer l'IMC
        Float imc = utilisateur.getPoids() / ((utilisateur.getTaille()/100) * (utilisateur.getTaille()/100));

        // Mettre à jour l'utilisateur avec l'IMC calculé
        utilisateur.setImc(imc);

        // Mettre à jour l'interprétation de l'IMC
        utilisateur.updateInterpretationIMC();
        String interpretation = utilisateur.getInterpretationIMC();

        utilisateurService.updateUtilisateur(id, utilisateur);

        logger.info("IMC calculé et mis à jour pour l'utilisateur {}: {}, Interprétation: {}",
                  id, imc, interpretation);

        // Ne retourner que l'IMC sans l'interprétation
        return ResponseEntity.ok(Map.of(
            "message", "IMC calculé et mis à jour avec succès",
            "imc", imc,
            "poids", utilisateur.getPoids(),
            "taille", utilisateur.getTaille()
        ));
    }

    // Endpoint pour recalculer l'IMC de tous les utilisateurs (réservé à l'admin)
    @PostMapping("/recalculer-tous-imc")
    public ResponseEntity<?> recalculerTousImc() {
        logger.info("Recalcul de l'IMC pour tous les utilisateurs");

        // Utiliser la méthode du service pour recalculer tous les IMC
        int compteurMisesAJour = utilisateurService.recalculerTousLesIMC();

        logger.info("Recalcul de l'IMC terminé: {} utilisateurs mis à jour", compteurMisesAJour);

        return ResponseEntity.ok(Map.of(
            "message", "Recalcul de l'IMC terminé",
            "mises_a_jour", compteurMisesAJour
        ));
    }

    // Endpoint pour récupérer l'IMC avec son interprétation
    @GetMapping(value = "/{id}/imc-avec-interpretation")
    public ResponseEntity<?> getImcAvecInterpretation(@PathVariable String id, HttpServletRequest request) {
        // Log pour déboguer
        logger.info("Endpoint GET IMC avec interprétation appelé pour l'utilisateur avec ID: {}", id);

        // Vérifier si l'utilisateur est autorisé
        if (!authorizationService.isAuthorizedToAccessResource(id, request)) {
            logger.error("User not authorized to access IMC for user ID: {}", id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Récupérer l'utilisateur directement
        Utilisateurs utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            logger.error("Utilisateur avec ID {} non trouvé", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Utilisateur non trouvé"));
        }

        // Récupérer l'IMC directement depuis l'entité utilisateur
        Float imc = utilisateur.getImc();
        String interpretation = utilisateur.getInterpretationIMC();

        logger.info("Utilisateur trouvé - Email: {}, Poids: {}, Taille: {}, IMC stocké: {}, Interprétation: {}",
                utilisateur.getEmail(), utilisateur.getPoids(), utilisateur.getTaille(), imc, interpretation);

        // Si l'IMC n'est pas encore calculé mais que les données sont disponibles, le calculer maintenant
        if (imc == null && utilisateur.getPoids() != null && utilisateur.getTaille() != null && utilisateur.getTaille() > 0) {
            // Calculer l'IMC manuellement
            imc = utilisateur.getPoids() / ((utilisateur.getTaille()/100) * (utilisateur.getTaille()/100));

            // Mettre à jour l'utilisateur avec l'IMC calculé
            utilisateur.setImc(imc);

            // Mettre à jour l'interprétation de l'IMC
            utilisateur.updateInterpretationIMC();
            interpretation = utilisateur.getInterpretationIMC();

            utilisateurService.updateUtilisateur(id, utilisateur);

            logger.info("IMC calculé à la volée et stocké: {}, Interprétation: {}", imc, interpretation);
        }

        if (imc == null) {
            logger.warn("IMC non disponible pour l'utilisateur {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "IMC non disponible pour cet utilisateur"));
        }

        logger.info("IMC avec interprétation retourné avec succès: {}, Interprétation: {}", imc, interpretation);
        return ResponseEntity.ok(Map.of(
            "imc", imc,
            "interpretation", interpretation != null ? interpretation : "Non disponible"
        ));
    }

    /**
     * Add a nutritionist to the user's favorites list
     *
     * @param userId The ID of the user
     * @param nutritionistId The ID of the nutritionist to add to favorites
     * @param request The HTTP request
     * @return The updated user DTO with the new favorite nutritionist added
     */
    @PostMapping("/{userId}/favorites/{nutritionistId}")
    public ResponseEntity<?> addFavoriteNutritionist(
            @PathVariable String userId,
            @PathVariable String nutritionistId,
            HttpServletRequest request
    ) {
        logger.info("Adding nutritionist {} to favorites for user {}", nutritionistId, userId);

        // Check if user is authorized
        if (!authorizationService.isAuthorizedToAccessResource(userId, request)) {
            logger.error("User not authorized to modify favorites for user ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Verify that the nutritionist exists
        if (!nutritionistRepository.existsById(nutritionistId)) {
            logger.error("Nutritionist with ID {} not found", nutritionistId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Nutritionist not found with ID: " + nutritionistId));
        }

        try {
            Utilisateurs updatedUser = utilisateurService.addFavoriteNutritionist(userId, nutritionistId);

            if (updatedUser == null) {
                logger.error("User with ID {} not found", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            return ResponseEntity.ok(Map.of(
                "message", "Nutritionist added to favorites successfully",
                "user", utilisateursMapper.mapTo(updatedUser)
            ));

        } catch (Exception e) {
            logger.error("Error adding nutritionist to favorites: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error adding nutritionist to favorites: " + e.getMessage()));
        }
    }

    /**
     * Remove a nutritionist from the user's favorites list
     *
     * @param userId The ID of the user
     * @param nutritionistId The ID of the nutritionist to remove from favorites
     * @param request The HTTP request
     * @return Success or error response
     */
    @DeleteMapping("/{userId}/favorites/{nutritionistId}")
    public ResponseEntity<?> removeFavoriteNutritionist(
            @PathVariable String userId,
            @PathVariable String nutritionistId,
            HttpServletRequest request
    ) {
        logger.info("Removing nutritionist {} from favorites for user {}", nutritionistId, userId);

        // Check if user is authorized
        if (!authorizationService.isAuthorizedToAccessResource(userId, request)) {
            logger.error("User not authorized to modify favorites for user ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            Utilisateurs updatedUser = utilisateurService.removeFavoriteNutritionist(userId, nutritionistId);

            if (updatedUser == null) {
                logger.error("User with ID {} not found", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            return ResponseEntity.ok(Map.of(
                "message", "Nutritionist removed from favorites successfully",
                "user", utilisateursMapper.mapTo(updatedUser)
            ));

        } catch (Exception e) {
            logger.error("Error removing nutritionist from favorites: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error removing nutritionist from favorites: " + e.getMessage()));
        }
    }

    /**
     * Get all favorite nutritionists for a user
     *
     * @param userId The ID of the user
     * @param request The HTTP request
     * @return List of nutritionist DTOs that are favorites of the user
     */
    @GetMapping("/{userId}/favorites")
    public ResponseEntity<?> getFavoriteNutritionists(
            @PathVariable String userId,
            HttpServletRequest request
    ) {
        logger.info("Getting favorite nutritionists for user {}", userId);

        // Check if user is authorized
        if (!authorizationService.isAuthorizedToAccessResource(userId, request)) {
            logger.error("User not authorized to access favorites for user ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            // Get the user to check if they exist
            Utilisateurs user = utilisateurService.getUtilisateurById(userId);
            if (user == null) {
                logger.error("User with ID {} not found", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            // Get the favorite nutritionists
            List<Nutrisioniste> favorites = utilisateurService.getFavoriteNutritionists(userId);

            // Convert to DTOs
            List<NutrisionisteDto> favoriteDtos = favorites.stream()
                    .map(nutritionistMapper::mapTo)
                    .collect(Collectors.toList());

            logger.info("Found {} favorite nutritionists for user {}", favoriteDtos.size(), userId);

            return ResponseEntity.ok(Map.of(
                "favorites", favoriteDtos,
                "count", favoriteDtos.size()
            ));

        } catch (Exception e) {
            logger.error("Error getting favorite nutritionists: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error getting favorite nutritionists: " + e.getMessage()));
        }
    }
}