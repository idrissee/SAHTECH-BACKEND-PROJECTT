package com.example.Sahtech.services.Impl.Auth_Author;

import com.example.Sahtech.Dto.auth.AuthResponse;
import com.example.Sahtech.Dto.auth.LoginRequest;
import com.example.Sahtech.Dto.auth.LogoutRequest;
import com.example.Sahtech.Dto.auth.LogoutResponse;
import com.example.Sahtech.Dto.auth.RegisterRequest;
import com.example.Sahtech.Enum.Maladie;
import com.example.Sahtech.entities.Users.Admin;
import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.Users.AdminRepository;
import com.example.Sahtech.repositories.Users.NutritionisteDetaille.NutritionisteRepository;
import com.example.Sahtech.repositories.Users.UtilisateursRepository;
import com.example.Sahtech.security.JwtTokenProvider;
import com.example.Sahtech.security.TokenBlacklistService;
import com.example.Sahtech.services.Interfaces.Auth_Author.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {


    private final AuthenticationManager authenticationManager;


    private final JwtTokenProvider tokenProvider;


    private final PasswordEncoder passwordEncoder;


    private final AdminRepository adminRepository;


    private final NutritionisteRepository nutrisionisteRepository;


    private final UtilisateursRepository utilisateursRepository;

    private  final TokenBlacklistService tokenBlacklistService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,JwtTokenProvider tokenProvider,PasswordEncoder passwordEncoder
    ,AdminRepository adminRepository,NutritionisteRepository nutrisionisteRepository,UtilisateursRepository utilisateursRepository ,TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.nutrisionisteRepository = nutrisionisteRepository;
        this.utilisateursRepository = utilisateursRepository;
        this.tokenBlacklistService = tokenBlacklistService;
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

        // Print the complete request object to see all fields
        System.out.println("DEBUG: Complete RegisterRequest object: " + registerRequest.toString());

        // Print debug info about received registration data
        System.out.println("DEBUG: Email: " + email);
        System.out.println("DEBUG: User Type: " + userType);
        System.out.println("DEBUG: Date of Birth: " + registerRequest.getDateDeNaissance());
        System.out.println("DEBUG: Has Chronic Disease: " + registerRequest.getHasChronicDisease());
        System.out.println("DEBUG: Maladies: " + registerRequest.getMaladies());
        System.out.println("DEBUG: Has Allergies: " + registerRequest.getHasAllergies());
        System.out.println("DEBUG: Allergies: " + registerRequest.getAllergies());
        System.out.println("DEBUG: Objectives: " + registerRequest.getObjectives());
        System.out.println("DEBUG: Photo URL: " + registerRequest.getPhotoUrl());

        // Check if email already exists
        if (emailExistsInAnyRepository(email)) {
            throw new RuntimeException("Email already in use");
        }

        // Parse date of birth if provided
        Date dateDeNaissance = new Date(); // Default to current date
        if (registerRequest.getDateDeNaissance() != null && !registerRequest.getDateDeNaissance().isEmpty()) {
            try {
                // Assuming date format like "yyyy-MM-dd"
                dateDeNaissance = java.sql.Date.valueOf(registerRequest.getDateDeNaissance());
                System.out.println("DEBUG: Parsed date of birth: " + dateDeNaissance);
            } catch (Exception e) {
                System.out.println("ERROR: Failed to parse date of birth: " + e.getMessage());
                // Continue with default date if parsing fails
            }
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
                        .dateDeNaissance(dateDeNaissance)
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
                        .dateDeNaissance(dateDeNaissance)
                        .estVerifie(false)
                        .provider("LOCAL")
                        .type("NUTRITIONIST")
                        .maladies(convertToMaladies(registerRequest.getMaladies()))
                        .allergies(registerRequest.getAllergies() != null ? registerRequest.getAllergies() : new ArrayList<>())
                        .objectives(registerRequest.getObjectives() != null ? registerRequest.getObjectives() : new ArrayList<>())
                        .hasChronicDisease(registerRequest.getHasChronicDisease() != null ? registerRequest.getHasChronicDisease() : false)
                        .hasAllergies(registerRequest.getHasAllergies() != null ? registerRequest.getHasAllergies() : false)
                        .preferredLanguage(registerRequest.getPreferredLanguage())
                        .poids(registerRequest.getPoids())
                        .taille(registerRequest.getTaille())
                        .photoUrl(registerRequest.getPhotoUrl())
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
                        .dateDeNaissance(dateDeNaissance)
                        .provider("LOCAL")
                        .type("USER")
                        .maladies(convertToMaladies(registerRequest.getMaladies()))
                        .allergies(registerRequest.getAllergies() != null ? registerRequest.getAllergies() : new ArrayList<>())
                        .objectives(registerRequest.getObjectives() != null ? registerRequest.getObjectives() : new ArrayList<>())
                        .hasChronicDisease(registerRequest.getHasChronicDisease() != null ? registerRequest.getHasChronicDisease() : false)
                        .hasAllergies(registerRequest.getHasAllergies() != null ? registerRequest.getHasAllergies() : false)
                        .preferredLanguage(registerRequest.getPreferredLanguage())
                        .poids(registerRequest.getPoids())
                        .taille(registerRequest.getTaille())
                        .sport(registerRequest.getDoesExercise())
                        .photoUrl(registerRequest.getPhotoUrl())
                        .build();
                 utilisateursRepository.save(user);
                break;

            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        // Return login response
        return login(new LoginRequest(email, registerRequest.getPassword(), userType));
    }

    // Helper method to convert string list to Maladie enum list
    private List<Maladie> convertToMaladies(List<String> maladiesString) {
        if (maladiesString == null || maladiesString.isEmpty()) {
            System.out.println("DEBUG: No chronic diseases to convert");
            return new ArrayList<>();
        }
        
        System.out.println("DEBUG: Converting chronic diseases: " + maladiesString);
        
        List<Maladie> result = new ArrayList<>();
        for (String maladieStr : maladiesString) {
            try {
                // First try direct mapping from Flutter disease names to Maladie enum
                if (maladieStr == null || maladieStr.trim().isEmpty()) {
                    continue; // Skip empty strings
                }
                
                String input = maladieStr.toLowerCase().trim();
                System.out.println("DEBUG: Processing disease: '" + input + "'");
                
                // Direct mappings from Flutter UI disease names to enum values
                if (input.equals("diabète") || input.contains("diabet")) {
                    result.add(Maladie.DIABETIQUE);
                    System.out.println("DEBUG: Mapped to DIABETIQUE enum");
                    continue;
                }
                
                if (input.equals("obésité") || input.contains("obesit")) {
                    result.add(Maladie.OBESITE);
                    System.out.println("DEBUG: Mapped to OBESITE enum");
                    continue;
                }
                
                if (input.equals("hypertension artérielle") || input.contains("hypertens")) {
                    result.add(Maladie.HYPERTENSION);
                    System.out.println("DEBUG: Mapped to HYPERTENSION enum");
                    continue;
                }
                
                if (input.equals("maladie coeliaque") || input.contains("coeliaq") || input.contains("celiaq")) {
                    result.add(Maladie.MALADIE_COELIAQUE);
                    System.out.println("DEBUG: Mapped to MALADIE_COELIAQUE enum");
                    continue;
                }
                
                if (input.equals("asthme") || input.contains("asthm")) {
                    result.add(Maladie.ASTHME);
                    System.out.println("DEBUG: Mapped to ASTHME enum");
                    continue;
                }
                
                if (input.contains("cholesterol") || input.contains("cholestérol")) {
                    result.add(Maladie.CHOLESTEROL);
                    System.out.println("DEBUG: Mapped to CHOLESTEROL enum");
                    continue;
                }
                
                if (input.contains("cardiaque") || input.contains("coeur")) {
                    result.add(Maladie.MALADIE_CARDIAQUE);
                    System.out.println("DEBUG: Mapped to MALADIE_CARDIAQUE enum");
                    continue;
                }
                
                if (input.contains("intoler") && input.contains("lactose")) {
                    result.add(Maladie.INTOLERANCE_LACTOSE);
                    System.out.println("DEBUG: Mapped to INTOLERANCE_LACTOSE enum");
                    continue;
                }
                
                if ((input.contains("allergie") || input.contains("allergique")) && input.contains("arachide")) {
                    result.add(Maladie.ALLERGIE_ARACHIDES);
                    System.out.println("DEBUG: Mapped to ALLERGIE_ARACHIDES enum");
                    continue;
                }
                
                if ((input.contains("allergie") || input.contains("allergique")) && input.contains("gluten")) {
                    result.add(Maladie.ALLERGIE_GLUTEN);
                    System.out.println("DEBUG: Mapped to ALLERGIE_GLUTEN enum");
                    continue;
                }
                
                if ((input.contains("allergie") || input.contains("allergique")) && 
                    (input.contains("fruit") && input.contains("mer"))) {
                    result.add(Maladie.ALLERGIE_FRUITS_DE_MER);
                    System.out.println("DEBUG: Mapped to ALLERGIE_FRUITS_DE_MER enum");
                    continue;
                }
                
                if (input.contains("ren") && (input.contains("maladie") || input.contains("problem"))) {
                    result.add(Maladie.MALADIE_RENALE);
                    System.out.println("DEBUG: Mapped to MALADIE_RENALE enum");
                    continue;
                }
                
                if (input.contains("hepat")) {
                    result.add(Maladie.HEPATITE);
                    System.out.println("DEBUG: Mapped to HEPATITE enum");
                    continue;
                }
                
                // If no direct mapping found, try the general approach with normalization
                String normalized = maladieStr.toUpperCase()
                    .replace(" ", "_")
                    .replace("É", "E")
                    .replace("È", "E")
                    .replace("Ê", "E")
                    .replace("À", "A")
                    .replace("Â", "A")
                    .replace("'", "_")
                    .replace("-", "_");
                
                System.out.println("DEBUG: Normalized '" + maladieStr + "' to '" + normalized + "'");
                
                // For any other cases, try exact enum matching
                try {
                    Maladie maladie = Maladie.valueOf(normalized);
                    result.add(maladie);
                    System.out.println("DEBUG: Successfully mapped to enum: " + maladie);
                } catch (IllegalArgumentException e) {
                    System.out.println("DEBUG: Failed to map normalized string to enum: " + normalized);
                    
                    // Try a more flexible approach - find enum that contains part of the string
                    boolean found = false;
                    for (Maladie m : Maladie.values()) {
                        if (normalized.contains(m.name()) || m.name().contains(normalized)) {
                            result.add(m);
                            System.out.println("DEBUG: Found partial match with enum: " + m);
                            found = true;
                            break;
                        }
                    }
                    
                    if (!found) {
                        System.out.println("WARNING: Could not map disease: " + maladieStr + " to any enum value");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR processing disease: " + maladieStr + " - " + e.getMessage());
            }
        }
        
        System.out.println("DEBUG: Final converted diseases: " + result);
        return result;
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
///lombok