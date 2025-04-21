package com.example.Sahtech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Classe de configuration qui teste la connexion Cloudinary au démarrage
 */
@Configuration
public class CloudinaryStartupTester {
    
    @Autowired
    private CloudinaryConfig cloudinaryConfig;
    
    @Autowired
    private Environment env;
    
    @Bean
    CommandLineRunner testCloudinaryConnection() {
        return args -> {
            try {
                System.out.println("\n=== TEST DE CONFIGURATION CLOUDINARY ===");
                
                // Afficher les propriétés
                System.out.println("Propriétés configurées dans application.properties:");
                String cloudName = env.getProperty("cloudinary.cloud_name");
                String apiKey = env.getProperty("cloudinary.api_key");
                String apiSecret = env.getProperty("cloudinary.api_secret");
                String cloudinaryUrl = env.getProperty("cloudinary.url");
                
                System.out.println("Cloud Name: " + cloudName);
                System.out.println("API Key: " + hidePartially(apiKey));
                System.out.println("API Secret: " + hidePartially(apiSecret));
                System.out.println("URL complète: " + (cloudinaryUrl != null ? "configurée" : "non configurée"));
                
                // Tester l'initialisation
                boolean testResult = cloudinaryConfig.simpleConnectionTest();
                
                if (testResult) {
                    System.out.println("✅ Initialisation de Cloudinary réussie!");
                } else {
                    System.err.println("❌ ERREUR: L'initialisation de Cloudinary a échoué!");
                }
                
                // Vérifier format des identifiants et suggestions
                if (cloudName == null || cloudName.trim().isEmpty()) {
                    System.err.println("❌ ERREUR: cloudinary.cloud_name est manquant ou vide");
                }
                if (apiKey == null || apiKey.trim().isEmpty()) {
                    System.err.println("❌ ERREUR: cloudinary.api_key est manquant ou vide");
                }
                if (apiSecret == null || apiSecret.trim().isEmpty()) {
                    System.err.println("❌ ERREUR: cloudinary.api_secret est manquant ou vide");
                }
                
                // Suggestions de configuration
                System.out.println("\nFormat correct pour application.properties:");
                System.out.println("cloudinary.cloud_name=votre_cloud_name");
                System.out.println("cloudinary.api_key=votre_api_key");
                System.out.println("cloudinary.api_secret=votre_api_secret");
                System.out.println("cloudinary.url=cloudinary://${cloudinary.api_key}:${cloudinary.api_secret}@${cloudinary.cloud_name}");
                
                System.out.println("======================================\n");
            } catch (Exception e) {
                System.err.println("❌ Exception lors du test de configuration Cloudinary: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
    
    /**
     * Masque partiellement une chaîne sensible pour l'affichage des logs
     */
    private String hidePartially(String value) {
        if (value == null || value.length() <= 4) {
            return "*****";
        }
        // Affiche seulement les 2 premiers et 2 derniers caractères
        return value.substring(0, 2) + "****" + value.substring(value.length() - 2);
    }
} 