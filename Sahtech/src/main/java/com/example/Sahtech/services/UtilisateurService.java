package com.example.Sahtech.services;


import com.example.Sahtech.entities.Utilisateurs;

import java.util.List;

public interface UtilisateurService {

    public Utilisateurs getUtilisateurById(int id);
    public List<Utilisateurs> getAllUtilisateurs();
    public List<Utilisateurs> getUtilisateursByName(String nom);
    public List<Utilisateurs> getUtilisateursByEmail(String email);
    public List<Utilisateurs> getUtilisateursByRole(String role);
    public Utilisateurs addUtilisateur(Utilisateurs u);
    public Utilisateurs updateUtilisateur(Utilisateurs u);
    public void deleteUtilisateur(int id);

}
