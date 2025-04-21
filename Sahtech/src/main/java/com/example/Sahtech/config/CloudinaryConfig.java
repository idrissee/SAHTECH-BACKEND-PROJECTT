package com.example.Sahtech.config;

import com.cloudinary.Cloudinary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    /**
     * Crée et configure le bean Cloudinary
     * Assurez-vous que les identifiants sont correctement configurés dans application.properties
     */
    @Bean
    public Cloudinary cloudinary() {
        try {
            // Configuration via Map
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", cloudName);
            config.put("api_key", apiKey);
            config.put("api_secret", apiSecret);
            
            // Réglages de sécurité renforcés
            config.put("secure", "true");
            
            System.out.println("Configuration Cloudinary initialisée avec:");
            System.out.println("- Cloud Name: " + cloudName);
            System.out.println("- API Key: " + apiKey.substring(0, 3) + "..." + (apiKey.length() > 3 ? apiKey.substring(apiKey.length() - 3) : ""));
            
            // Création de l'instance Cloudinary
            return new Cloudinary(config);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'initialisation de Cloudinary: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback à une configuration directe par URL
            System.out.println("Tentative de configuration alternative par URL...");
            String cloudinaryUrl = "cloudinary://" + apiKey + ":" + apiSecret + "@" + cloudName;
            return new Cloudinary(cloudinaryUrl);
        }
    }
    
    /**
     * Méthode alternative simple pour tester la connexion
     */
    public boolean simpleConnectionTest() {
        try {
            // Utiliser une méthode plus simple pour tester
            Cloudinary cloudinaryInstance = cloudinary();
            System.out.println("Instance Cloudinary créée avec succès: " + cloudinaryInstance.toString());
            System.out.println("Configuration: " + cloudinaryInstance.config.toString());
            
            // Si on arrive ici sans exception, c'est déjà un bon signe
            return true;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du test simple: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
