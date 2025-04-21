package com.example.Sahtech.security;

import com.example.Sahtech.entities.Admin;
import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.AdminRepository;
import com.example.Sahtech.repositories.NutritionisteRepository;
import com.example.Sahtech.repositories.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private NutritionisteRepository nutrisionisteRepository;
    
    @Autowired
    private UtilisateursRepository utilisateursRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First try to find by email in any repository
        Optional<Utilisateurs> utilisateur = utilisateursRepository.findByEmail(email);
        if (utilisateur.isPresent()) {
            return createUserDetailsFromUtilisateur(utilisateur.get());
        }
        
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return createUserDetailsFromUtilisateur(admin.get());
        }
        
        Optional<Nutrisioniste> nutritionist = nutrisionisteRepository.findByEmail(email);
        if (nutritionist.isPresent()) {
            return createUserDetailsFromUtilisateur(nutritionist.get());
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    public UserDetails loadUserByEmailAndType(String email, String userType) throws UsernameNotFoundException {
        switch (userType.toUpperCase()) {
            case "ADMIN":
                Optional<Admin> adminOpt = adminRepository.findByEmail(email);
                if (adminOpt.isPresent()) {
                    return createUserDetailsFromUtilisateur(adminOpt.get());
                }
                break;
            case "NUTRITIONIST":
                Optional<Nutrisioniste> nutrisionisteOpt = nutrisionisteRepository.findByEmail(email);
                if (nutrisionisteOpt.isPresent()) {
                    return createUserDetailsFromUtilisateur(nutrisionisteOpt.get());
                }
                break;
            case "USER":
                Optional<Utilisateurs> userOpt = utilisateursRepository.findByEmail(email);
                if (userOpt.isPresent()) {
                    return createUserDetailsFromUtilisateur(userOpt.get());
                }
                break;
        }

        throw new UsernameNotFoundException("User not found with email: " + email + " and type: " + userType);
    }
    
    private UserDetails createUserDetailsFromUtilisateur(Utilisateurs utilisateur) {
        String role;
        if (utilisateur instanceof Admin) {
            role = "ROLE_ADMIN";
        } else if (utilisateur instanceof Nutrisioniste) {
            role = "ROLE_NUTRITIONIST";
        } else {
            role = "ROLE_USER";
        }
        
        return new User(
            utilisateur.getEmail(),
            utilisateur.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}