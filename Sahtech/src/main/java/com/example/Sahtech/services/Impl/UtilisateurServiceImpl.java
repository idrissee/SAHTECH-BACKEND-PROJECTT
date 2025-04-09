package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.UtilisateursRepository;
import com.example.Sahtech.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateursRepository utilisateursRepository;

    @Override
    public Utilisateurs getUtilisateurById(Long id) {
        Optional<Utilisateurs> utilisateur = utilisateursRepository.findById(id);
        return utilisateur.orElse(null);
    }

    @Override
    public List<Utilisateurs> getAllUtilisateurs() {
        return utilisateursRepository.findAll();
    }

    @Override
    public List<Utilisateurs> getUtilisateursByName(String nom) {
        return utilisateursRepository.findByNom(nom);
    }

    @Override
    public List<Utilisateurs> getUtilisateursByEmail(String email) {
        return utilisateursRepository.findByEmail(email);
    }

    @Override
    public List<Utilisateurs> getUtilisateursByRole(String role) {
        return utilisateursRepository.findByRole(role);
    }

    @Override
    public Utilisateurs addUtilisateur(Utilisateurs u) {
        return utilisateursRepository.save(u);
    }

    @Override
    public Utilisateurs updateUtilisateur(Utilisateurs u) {
        if (utilisateursRepository.existsById(u.getId())) {
            return utilisateursRepository.save(u);
        }
        return null;
    }

    @Override
    public boolean deleteUtilisateur(Long id) {
        if (utilisateursRepository.existsById(id)) {
            utilisateursRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
