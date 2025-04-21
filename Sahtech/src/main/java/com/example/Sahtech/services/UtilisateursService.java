package com.example.Sahtech.services;

import com.example.Sahtech.entities.Utilisateurs;

import java.util.List;
import java.util.Optional;

public interface UtilisateursService {
    List<Utilisateurs> getAllUtilisateurs();
    Utilisateurs getUtilisateurById(String id);
    Utilisateurs updateUtilisateur(String id, Utilisateurs utilisateur);
    boolean deleteUtilisateur(String id);

    Utilisateurs getUtilisateursByEmail(String email);

    Utilisateurs addUtilisateur(Utilisateurs user);
}