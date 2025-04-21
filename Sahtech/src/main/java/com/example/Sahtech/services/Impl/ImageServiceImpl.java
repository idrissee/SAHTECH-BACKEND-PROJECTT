package com.example.Sahtech.services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Sahtech.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    @Autowired
    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        try {
            // Valider le fichier
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("Le fichier est vide ou null");
            }
            
            System.out.println("Début du téléchargement de l'image...");
            System.out.println("Taille du fichier: " + file.getSize() + " octets");
            System.out.println("Type MIME: " + file.getContentType());
            
            // Générer un nom de fichier unique pour éviter les conflits
            String uniqueFilename = UUID.randomUUID().toString();
            
            // Options simplifiées pour Cloudinary
            Map<String, Object> options = ObjectUtils.asMap(
                "public_id", "sahtech/" + uniqueFilename,
                "overwrite", true
            );
            
            System.out.println("Envoi vers Cloudinary avec options: " + options);
            
            // Télécharger l'image avec moins d'options pour minimiser les erreurs
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);
            
            // Afficher les détails pour le débogage
            System.out.println("✅ Image téléchargée avec succès");
            System.out.println("URL: " + result.get("secure_url"));
            System.out.println("Public ID: " + result.get("public_id"));
            
            // Retourner l'URL sécurisée de l'image
            return (String) result.get("secure_url");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors du téléchargement de l'image: " + e.getMessage());
            
            // Messages d'erreur spécifiques
            if (e.getMessage().contains("Invalid Signature")) {
                System.err.println("Erreur d'authentification: vérifiez vos identifiants API dans application.properties");
                System.err.println("Assurez-vous que api_key, api_secret et cloud_name sont corrects");
            } else if (e.getMessage().contains("timed out")) {
                System.err.println("Délai d'attente dépassé: vérifiez votre connexion Internet");
            } else if (e.getMessage().contains("resource_not_found")) {
                System.err.println("Ressource non trouvée: vérifiez que votre cloud_name est correct");
            }
            
            e.printStackTrace();
            throw new IOException("Échec du téléchargement de l'image: " + e.getMessage(), e);
        }
    }
}
