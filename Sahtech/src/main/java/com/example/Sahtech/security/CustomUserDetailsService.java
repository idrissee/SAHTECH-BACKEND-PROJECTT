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
import java.util.List;
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
        // First check admin
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return new User(
                admin.getEmail(),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }
        
        // Then check nutritionist
        Optional<Nutrisioniste> nutrisionisteOpt = nutrisionisteRepository.findByEmail(email);
        if (nutrisionisteOpt.isPresent()) {
            Nutrisioniste nutritionist = nutrisionisteOpt.get();
            return new User(
                nutritionist.getEmail(),
                nutritionist.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_NUTRITIONIST"))
            );
        }
        
        // Finally check regular user
        Optional<Utilisateurs> userOpt = utilisateursRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            Utilisateurs user = userOpt.get();
            return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }
        
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
    
    public UserDetails loadUserByEmailAndType(String email, String userType) throws UsernameNotFoundException {
        switch (userType.toUpperCase()) {
            case "ADMIN":
                Optional<Admin> adminOpt = adminRepository.findByEmail(email);
                if (adminOpt.isPresent()) {
                    Admin admin = adminOpt.get();
                    return new User(
                        admin.getEmail(),
                        admin.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                    );
                }
                break;
            case "NUTRITIONIST":
                Optional<Nutrisioniste> nutrisionisteOpt = nutrisionisteRepository.findByEmail(email);
                if (nutrisionisteOpt.isPresent()) {
                    Nutrisioniste nutritionist = nutrisionisteOpt.get();
                    return new User(
                        nutritionist.getEmail(),
                        nutritionist.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_NUTRITIONIST"))
                    );
                }
                break;
            case "USER":
                Optional<Utilisateurs> userOpt = utilisateursRepository.findByEmail(email);
                if (userOpt.isPresent()) {
                    Utilisateurs user = userOpt.get();
                    return new User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                }
                break;
        }
        
        throw new UsernameNotFoundException("User not found with email: " + email + " and type: " + userType);
    }
} 