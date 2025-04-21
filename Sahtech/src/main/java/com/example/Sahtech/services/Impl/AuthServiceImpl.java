package com.example.Sahtech.services.Impl;

import com.example.Sahtech.Dto.auth.AuthResponse;
import com.example.Sahtech.Dto.auth.LoginRequest;
import com.example.Sahtech.Dto.auth.LogoutRequest;
import com.example.Sahtech.Dto.auth.LogoutResponse;
import com.example.Sahtech.Dto.auth.RegisterRequest;
import com.example.Sahtech.entities.Admin;
import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.AdminRepository;
import com.example.Sahtech.repositories.NutritionisteRepository;
import com.example.Sahtech.repositories.UtilisateursRepository;
import com.example.Sahtech.security.JwtTokenProvider;
import com.example.Sahtech.security.TokenBlacklistService;
import com.example.Sahtech.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {


    private final AuthenticationManager authenticationManager;


    private final JwtTokenProvider tokenProvider;


    private final PasswordEncoder passwordEncoder;


    private final AdminRepository adminRepository;


    private final NutritionisteRepository nutrisionisteRepository;


    private final UtilisateursRepository utilisateursRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager,JwtTokenProvider tokenProvider,PasswordEncoder passwordEncoder
    ,AdminRepository adminRepository,NutritionisteRepository nutrisionisteRepository,UtilisateursRepository utilisateursRepository){
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.nutrisionisteRepository = nutrisionisteRepository;
        this.utilisateursRepository = utilisateursRepository;
    }


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String userType = loginRequest.getUserType();

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );


        // Get user ID based on type
        String userId = getUserIdByEmailAndType(email, userType);

        // Generate token
        String token = tokenProvider.generateToken(authentication, userType, userId);

        // Build response
        return AuthResponse.builder()
                .token(token)
                .userType(userType)
                .userId(userId)
                .email(email)
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = passwordEncoder.encode(registerRequest.getPassword());
        String userType = registerRequest.getUserType();

        // Check if email already exists
        if (emailExistsInAnyRepository(email)) {
            throw new RuntimeException("Email already in use");
        }

        // Create appropriate user based on type
        switch (userType.toUpperCase()) {
            case "ADMIN":
                Admin admin = Admin.builder()
                        .nom(registerRequest.getNom())
                        .prenom(registerRequest.getPrenom())
                        .email(email)
                        .password(password)
                        .numTelephone(registerRequest.getTelephone())
                        .dateDeNaissance(new Date())
                        .provider("LOCAL")
                        .type("ADMIN")
                        .build();
                 adminRepository.save(admin);
                break;

            case "NUTRITIONIST":
                Nutrisioniste nutritionist = Nutrisioniste.builder()
                        .nom(registerRequest.getNom())
                        .prenom(registerRequest.getPrenom())
                        .email(email)
                        .password(password)
                        .numTelephone(registerRequest.getTelephone())
                        .dateDeNaissance(new Date())
                        .estVerifie(false)
                        .provider("LOCAL")
                        .type("NUTRITIONIST")
                        .maladies(new ArrayList<>())
                        .allergies(new ArrayList<>())
                        .objectives(new ArrayList<>())
                        .physicalActivities(new ArrayList<>())
                        .dailyActivities(new ArrayList<>())
                        .healthGoals(new ArrayList<>())
                        .hasChronicDisease(registerRequest.getHasChronicDisease())
                        .hasAllergies(registerRequest.getHasAllergies())
                        .preferredLanguage(registerRequest.getPreferredLanguage())
                        .build();
                 nutrisionisteRepository.save(nutritionist);
                break;

            case "USER":
                Utilisateurs user = Utilisateurs.builder()
                        .nom(registerRequest.getNom())
                        .prenom(registerRequest.getPrenom())
                        .email(email)
                        .password(password)
                        .numTelephone(registerRequest.getTelephone())
                        .dateDeNaissance(new Date())
                        .provider("LOCAL")
                        .type("USER")
                        .maladies(new ArrayList<>())
                        .allergies(new ArrayList<>())
                        .objectives(new ArrayList<>())
                        .physicalActivities(new ArrayList<>())
                        .dailyActivities(new ArrayList<>())
                        .healthGoals(new ArrayList<>())
                        .hasChronicDisease(registerRequest.getHasChronicDisease())
                        .hasAllergies(registerRequest.getHasAllergies())
                        .preferredLanguage(registerRequest.getPreferredLanguage())
                        .build();
                 utilisateursRepository.save(user);
                break;

            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        // Return login response
        return login(new LoginRequest(email, registerRequest.getPassword(), userType));
    }


    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        String token = logoutRequest.getToken();

        // Check if the token is valid
        if (!tokenProvider.validateToken(token)) {
            return LogoutResponse.builder()
                    .success(false)
                    .message("Invalid token")
                    .build();
        }

        try {
            // Extract expiration time from token
            Date expiryDate = tokenProvider.getExpirationDateFromToken(token);
            long expiryTimeMillis = expiryDate.getTime();

            // Add the token to the blacklist
            tokenBlacklistService.blacklistToken(token, expiryTimeMillis);

            return LogoutResponse.builder()
                    .success(true)
                    .message("Logged out successfully")
                    .build();
        } catch (Exception e) {
            return LogoutResponse.builder()
                    .success(false)
                    .message("Error during logout: " + e.getMessage())
                    .build();
        }
    }

    private boolean emailExistsInAnyRepository(String email) {
        return adminRepository.existsByEmail(email) ||
               nutrisionisteRepository.existsByEmail(email) ||
               utilisateursRepository.existsByEmail(email);
    }

    private String getUserIdByEmailAndType(String email, String userType) {
        switch (userType.toUpperCase()) {
            case "ADMIN":
                Optional<Admin> admin = adminRepository.findByEmail(email);
                return admin.map(Admin::getId).orElse(null);
            case "NUTRITIONIST":
                Optional<Nutrisioniste> nutritionist = nutrisionisteRepository.findByEmail(email);
                return nutritionist.map(Nutrisioniste::getId).orElse(null);
            case "USER":
                Optional<Utilisateurs> user = utilisateursRepository.findByEmail(email);
                return user.map(Utilisateurs::getId).orElse(null);
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }
}