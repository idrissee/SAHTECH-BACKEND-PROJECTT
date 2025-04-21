package com.example.Sahtech.services.Impl;

import com.example.Sahtech.security.JwtTokenProvider;
import com.example.Sahtech.entities.Nutrisioniste;
import com.example.Sahtech.services.AuthorizationService;
import com.example.Sahtech.services.HistoriqueScanService;
import com.example.Sahtech.services.NutrisionisteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private NutrisionisteService nutrisionisteService;

    @Autowired
    private HistoriqueScanService historiqueScanService;

    /**
     * Vérifie si l'utilisateur courant est autorisé à accéder à une ressource utilisateur spécifique
     * @param resourceId ID de la ressource (utilisateur, nutritionniste, etc.)
     * @param request Requête HTTP pour extraire le token
     * @return true si l'utilisateur est un admin ou s'il accède à ses propres données
     */
    @Override
    public boolean isAuthorizedToAccessResource(String resourceId, HttpServletRequest request) {
        // Récupérer l'authentification courante
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Vérifier si l'utilisateur est un admin (ils peuvent tout faire)
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true;
        }
        
        // Extraire le token JWT
        String token = extractTokenFromRequest(request);
        if (token == null) {
            return false;
        }

        // Récupérer le type d'utilisateur et son ID à partir du token
        String userType = jwtTokenProvider.getUserType(token);
        String userId = jwtTokenProvider.getUserId(token);
        
        // Vérifier si l'utilisateur accède à ses propres données
        return userId.equals(resourceId);
    }
    
    /**
     * Extrait le token JWT de la requête HTTP
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Vérifie si un nutritionniste est autorisé à accéder à une localisation.
     * Un admin peut accéder à toutes les localisations.
     * Un nutritionniste ne peut accéder qu'à la localisation associée à son profil.
     */
    @Override
    public boolean isNutritionisteAuthorizedForLocation(String locationId, HttpServletRequest request) {
        // Vérifier si l'utilisateur est un admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true; // Les admins ont accès à tout
        }

        // Pour un nutritionniste, vérifier que c'est sa propre localisation
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_NUTRITIONIST"))) {
            try {
                // Extraire l'ID du nutritionniste du token JWT
                String token = extractTokenFromRequest(request);
                if (token != null) {
                    String userId = jwtTokenProvider.getUserId(token);

                    // Récupérer le nutritionniste
                    Nutrisioniste nutritionniste = nutrisionisteService.getNutrisionisteById(userId);

                    // Vérifier si la localisation correspond
                    return nutritionniste != null && locationId.equals(nutritionniste.getLocalisationId());
                }
            } catch (Exception e) {
                return false;
            }
        }

        return false; // Par défaut, refuser l'accès
    }

    /**
     * Vérifie si un utilisateur a déjà scanné un produit spécifique
     * Un admin peut accéder à tous les produits.
     * Un utilisateur ne peut accéder qu'aux produits qu'il a déjà scannés.
     */
    @Override
    public boolean hasUserScannedProduct(String produitId, HttpServletRequest request) {
        // Vérifier si l'utilisateur est un admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true; // Les admins ont accès à tout
        }

        // Pour un utilisateur standard, vérifier qu'il a déjà scanné ce produit
        try {
            // Extraire l'ID de l'utilisateur du token JWT
            String token = extractTokenFromRequest(request);
            if (token != null) {
                String userId = jwtTokenProvider.getUserId(token);

                // Vérifier si l'utilisateur a scanné ce produit
                return historiqueScanService.hasUserScannedProduct(userId, produitId);
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
} 