package com.example.Sahtech.services.Impl.Users;

import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.Users.UtilisateursRepository;
import com.example.Sahtech.services.Interfaces.Users.UtilisateursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UtilisateursServiceImpl implements UtilisateursService {
    
    private static final Logger logger = LoggerFactory.getLogger(UtilisateursServiceImpl.class);

    @Autowired
    private UtilisateursRepository utilisateursRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

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
}