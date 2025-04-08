package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.UtilisateursRepository;
import com.example.Sahtech.services.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateursRepository utilisateursRepository;

    @Override
    public Utilisateurs getUtilisateurById(int id) {
        return null;
    }

    @Override
    public List<Utilisateurs> getAllUtilisateurs() {
        return List.of();
    }

    @Override
    public List<Utilisateurs> getUtilisateursByName(String nom) {
        return List.of();
    }


    @Override
    public List<Utilisateurs> getUtilisateursByEmail(String email) {
        return utilisateursRepository.findByEmail(email); // à condition que cette méthode existe
    }
    @Override
    public List<Utilisateurs> getUtilisateursByRole(String role) {
        return List.of();
    }

    @Override
    public Utilisateurs addUtilisateur(Utilisateurs u) {
        return null;
    }

    @Override
    public Utilisateurs updateUtilisateur(Utilisateurs u) {
        return null;
    }

    @Override
    public void deleteUtilisateur(int id) {

    }
}
