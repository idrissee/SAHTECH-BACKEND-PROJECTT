package com.example.Sahtech.services.Interfaces.Users;

import com.example.Sahtech.entities.Users.Utilisateurs;

import java.util.List;

public interface UtilisateursService {
    List<Utilisateurs> getAllUtilisateurs();
    Utilisateurs getUtilisateurById(String id);
    Utilisateurs updateUtilisateur(String id, Utilisateurs utilisateur);
    boolean deleteUtilisateur(String id);

    Utilisateurs getUtilisateursByEmail(String email);

    Utilisateurs addUtilisateur(Utilisateurs user);
    Utilisateurs setPhotoUrl(String id, String photoUrl);

    boolean changePassword(String id, String currentPassword, String newPassword);
    
    Float getImcByUserId(String id);
    
    /**
     * Recalcule l'IMC pour tous les utilisateurs dans la base de données
     * @return Le nombre d'utilisateurs mis à jour
     */
    int recalculerTousLesIMC();
}