package com.example.Sahtech.services.Impl.Users;

import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.Users.NutritionisteDetaille.NutritionisteRepository;
import com.example.Sahtech.repositories.Users.UtilisateursRepository;
import com.example.Sahtech.services.Interfaces.Users.UtilisateursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UtilisateursServiceImpl implements UtilisateursService {
    
    private static final Logger logger = LoggerFactory.getLogger(UtilisateursServiceImpl.class);

    @Autowired
    private UtilisateursRepository utilisateursRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private NutritionisteRepository nutritionistRepository;

    @Override
    public List<Utilisateurs> getAllUtilisateurs() {
        return utilisateursRepository.findAll();
    }

    @Override
    public Utilisateurs getUtilisateurById(String id) {
        Optional<Utilisateurs> utilisateur = utilisateursRepository.findById(id);
        return utilisateur.orElse(null);
    }

    @Override
    public Utilisateurs getUtilisateursByEmail(String email) {
        Optional<Utilisateurs> utilisateur = utilisateursRepository.findByEmail(email);
        return utilisateur.orElse(null);
    }


    @Override
    public Utilisateurs updateUtilisateur(String id, Utilisateurs updatedUser) {
        logger.info("Updating user with ID: {}", id);
        
        if (utilisateursRepository.existsById(id)) {
            // Get the existing user to preserve fields that are not explicitly updated
            Optional<Utilisateurs> existingUserOpt = utilisateursRepository.findById(id);
            if (existingUserOpt.isPresent()) {
                Utilisateurs existingUser = existingUserOpt.get();
                
                // Set the ID to ensure we're updating the correct record
                updatedUser.setId(id);
                
                // Preserve password if not provided in update
                if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
                    updatedUser.setPassword(existingUser.getPassword());
                }
                
                // Special handling for disease-related fields
                // If hasChronicDisease is false, explicitly set empty lists for maladies and chronicConditions
                if (!updatedUser.isHasChronicDisease()) {
                    logger.info("User has no chronic diseases, explicitly setting empty lists");
                    // Use empty lists instead of null to ensure MongoDB keeps the fields
                    updatedUser.setMaladies(java.util.Collections.emptyList());
                    updatedUser.setChronicConditions(java.util.Collections.emptyList());
                    logger.info("Set empty disease lists - will be preserved in MongoDB");
                } else {
                    // Handle the case where we need to preserve existing diseases
                    if (updatedUser.getMaladies() == null && existingUser.getMaladies() != null) {
                        updatedUser.setMaladies(existingUser.getMaladies());
                    }
                    
                    if (updatedUser.getChronicConditions() == null && existingUser.getChronicConditions() != null) {
                        updatedUser.setChronicConditions(existingUser.getChronicConditions());
                    }
                }
                
                // Handle other empty lists - keep existing data if updated data is empty
                if (updatedUser.getAllergies() == null || updatedUser.getAllergies().isEmpty()) {
                    updatedUser.setAllergies(existingUser.getAllergies());
                }
                
                if (updatedUser.getObjectives() == null || updatedUser.getObjectives().isEmpty()) {
                    updatedUser.setObjectives(existingUser.getObjectives());
                }
                
                if (updatedUser.getPhysicalActivities() == null || updatedUser.getPhysicalActivities().isEmpty()) {
                    updatedUser.setPhysicalActivities(existingUser.getPhysicalActivities());
                }
                
                if (updatedUser.getDailyActivities() == null || updatedUser.getDailyActivities().isEmpty()) {
                    updatedUser.setDailyActivities(existingUser.getDailyActivities());
                }
                
                if (updatedUser.getHealthGoals() == null || updatedUser.getHealthGoals().isEmpty()) {
                    updatedUser.setHealthGoals(existingUser.getHealthGoals());
                }
                
                // Preserve favorite nutritionists if not included in the update
                if (updatedUser.getFavoriteNutritionistIds() == null || updatedUser.getFavoriteNutritionistIds().isEmpty()) {
                    updatedUser.setFavoriteNutritionistIds(existingUser.getFavoriteNutritionistIds());
                }
                
                // Save the updated user
                Utilisateurs savedUser = utilisateursRepository.save(updatedUser);
                logger.info("User updated successfully: {}", savedUser.getEmail());
                return savedUser;
            }
            
            // If we couldn't find the existing user (which should never happen as we checked exists), just save
            return utilisateursRepository.save(updatedUser);
        }
        logger.warn("User with ID {} not found for update", id);
        return null;
    }

    @Override
    public boolean deleteUtilisateur(String id) {
        if (utilisateursRepository.existsById(id)) {
            utilisateursRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Utilisateurs addUtilisateur(Utilisateurs user) {
        return utilisateursRepository.save(user);
    }


    @Override
    public Utilisateurs setPhotoUrl(String id, String photoUrl) {
        logger.info("Setting photoUrl for user with ID: {}", id);
        logger.info("New photoUrl: {}", photoUrl);
        
        Utilisateurs utilisateur = getUtilisateurById(id);
        if (utilisateur == null) {
            logger.error("User with ID {} not found when setting photoUrl", id);
            return null;
        }
        
        logger.info("Current photoUrl: {}", utilisateur.getPhotoUrl());
        utilisateur.setPhotoUrl(photoUrl);
        logger.info("Updated user object with new photoUrl: {}", utilisateur.getPhotoUrl());
        
        Utilisateurs savedUser = utilisateursRepository.save(utilisateur);
        logger.info("User saved with photoUrl: {}", savedUser.getPhotoUrl());
        
        return savedUser;
    }



    @Override
    public boolean changePassword(String id, String currentPassword, String newPassword) {
        logger.info("Changing password for user with ID: {}", id);
        
        Optional<Utilisateurs> userOpt = utilisateursRepository.findById(id);
        if (userOpt.isEmpty()) {
            logger.error("User with ID {} not found when changing password", id);
            return false;
        }
        
        Utilisateurs user = userOpt.get();
        
        // Check if the current password matches
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            logger.warn("Current password doesn't match for user ID: {}", id);
            return false;
        }
        
        // Encode and save the new password
        user.setPassword(passwordEncoder.encode(newPassword));
        utilisateursRepository.save(user);
        
        logger.info("Password changed successfully for user with ID: {}", id);
        return true;
    }
    
    @Override
    public Utilisateurs addFavoriteNutritionist(String userId, String nutritionistId) {
        logger.info("Adding nutritionist ID {} to favorites for user ID {}", nutritionistId, userId);
        
        // Verify that the user exists
        Utilisateurs user = getUtilisateurById(userId);
        if (user == null) {
            logger.error("User with ID {} not found when adding favorite nutritionist", userId);
            return null;
        }
        
        // Verify that the nutritionist exists
        Optional<Nutrisioniste> nutritionistOpt = nutritionistRepository.findById(nutritionistId);
        if (nutritionistOpt.isEmpty()) {
            logger.error("Nutritionist with ID {} not found when adding to favorites", nutritionistId);
            return null;
        }
        
        // Initialize the list if it's null
        if (user.getFavoriteNutritionistIds() == null) {
            user.setFavoriteNutritionistIds(new ArrayList<>());
        }
        
        // Check if the nutritionist is already in favorites to prevent duplicates
        if (!user.getFavoriteNutritionistIds().contains(nutritionistId)) {
            user.getFavoriteNutritionistIds().add(nutritionistId);
            logger.info("Added nutritionist ID {} to favorites for user ID {}", nutritionistId, userId);
        } else {
            logger.info("Nutritionist ID {} is already in favorites for user ID {}", nutritionistId, userId);
            return user; // Return the unchanged user if nutritionist is already in favorites
        }
        
        // Save and return the updated user
        Utilisateurs savedUser = utilisateursRepository.save(user);
        logger.info("User saved with updated favorites: {}", savedUser.getFavoriteNutritionistIds());
        
        return savedUser;
    }
    
    @Override
    public Utilisateurs removeFavoriteNutritionist(String userId, String nutritionistId) {
        logger.info("Removing nutritionist ID {} from favorites for user ID {}", nutritionistId, userId);
        
        // Verify that the user exists
        Utilisateurs user = getUtilisateurById(userId);
        if (user == null) {
            logger.error("User with ID {} not found when removing favorite nutritionist", userId);
            return null;
        }
        
        // Check if the list exists and contains the nutritionist
        if (user.getFavoriteNutritionistIds() != null) {
            boolean removed = user.getFavoriteNutritionistIds().remove(nutritionistId);
            if (removed) {
                logger.info("Removed nutritionist ID {} from favorites for user ID {}", nutritionistId, userId);
            } else {
                logger.info("Nutritionist ID {} was not in favorites for user ID {}", nutritionistId, userId);
            }
            
            // Save and return the updated user
            Utilisateurs savedUser = utilisateursRepository.save(user);
            logger.info("User saved with updated favorites: {}", savedUser.getFavoriteNutritionistIds());
            
            return savedUser;
        }
        
        // Return the unchanged user if no favorites list exists
        logger.info("No favorites list exists for user ID {}", userId);
        return user;
    }
    
    @Override
    public List<String> getFavoriteNutritionistIds(String userId) {
        logger.info("Getting favorite nutritionist IDs for user ID {}", userId);
        
        // Verify that the user exists
        Utilisateurs user = getUtilisateurById(userId);
        if (user == null) {
            logger.error("User with ID {} not found when getting favorite nutritionist IDs", userId);
            return new ArrayList<>();
        }
        
        // Return the list of favorite nutritionist IDs or an empty list if null
        List<String> favoriteIds = user.getFavoriteNutritionistIds();
        return favoriteIds != null ? favoriteIds : new ArrayList<>();
    }
    
    @Override
    public List<Nutrisioniste> getFavoriteNutritionists(String userId) {
        logger.info("Getting favorite nutritionists for user ID {}", userId);
        
        // Get the list of favorite nutritionist IDs
        List<String> favoriteIds = getFavoriteNutritionistIds(userId);
        
        // If the list is empty, return an empty list
        if (favoriteIds.isEmpty()) {
            logger.info("No favorite nutritionists found for user ID {}", userId);
            return new ArrayList<>();
        }
        
        // Fetch the nutritionist objects from the repository
        List<Nutrisioniste> favorites = favoriteIds.stream()
            .map(nutritionistRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        logger.info("Found {} favorite nutritionists for user ID {}", favorites.size(), userId);
        return favorites;
    }
}