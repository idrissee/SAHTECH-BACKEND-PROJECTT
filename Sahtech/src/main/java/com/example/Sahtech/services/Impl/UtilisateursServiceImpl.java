package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.UtilisateursRepository;
import com.example.Sahtech.services.UtilisateursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateursServiceImpl implements UtilisateursService {

    @Autowired
    private UtilisateursRepository utilisateursRepository;

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
    public Utilisateurs createUtilisateurs(Utilisateurs utilisateur) {
        return utilisateursRepository.save(utilisateur);
    }

    @Override
    public Utilisateurs updateUtilisateur(String id, Utilisateurs utilisateur) {
        if (utilisateursRepository.existsById(id)) {
            utilisateur.setId(id);
            return utilisateursRepository.save(utilisateur);
        }
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
        Utilisateurs utilisateur = getUtilisateurById(id);
        utilisateur.setPhotoUrl(photoUrl);
        return utilisateursRepository.save(utilisateur);
    }
}