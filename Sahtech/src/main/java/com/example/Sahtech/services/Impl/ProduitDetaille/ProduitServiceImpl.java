package com.example.Sahtech.services.Impl.ProduitDetaille;

import com.example.Sahtech.Enum.TypeProduit;
import com.example.Sahtech.Enum.ValeurNutriScore;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.repositories.ProduitDetaille.AdditifsRepository;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.NutriScoreService;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.ProduitService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;

    private final AdditifsRepository additifRepository;
    private final NutriScoreService nutriScoreService;

    public ProduitServiceImpl(ProduitRepository produitRepository, 
                            AdditifsRepository additifRepository,
                            NutriScoreService nutriScoreService) {
        this.produitRepository = produitRepository;
        this.additifRepository = additifRepository;
        this.nutriScoreService = nutriScoreService;
    }

    private String generateNutriScoreDescription(ValeurNutriScore nutriScore) {
        switch (nutriScore) {
            case A:
                return "Excellent choix nutritionnel";
            case B:
                return "Bon choix nutritionnel";
            case C:
                return "Choix nutritionnel moyen";
            case D:
                return "Choix nutritionnel à limiter";
            case E:
                return "Choix nutritionnel à éviter";
            default:
                return "Score nutritionnel non disponible";
        }
    }

    @Override
    public Produit createProduit(Produit produit, TypeProduit typeProduit) {
        if (typeProduit == null) {
            // Handle the case where typeProduit is null, e.g., throw an exception
            throw new IllegalArgumentException("TypeProduit cannot be null");
        }

        // IMPORTANT DEBUG LOG
        System.out.println("CREATING PRODUCT: " + produit.getNom() + " WITH BARCODE: " + produit.getCodeBarre());
        
        // Calculer le NutriScore
        ValeurNutriScore nutriScore = nutriScoreService.calculateNutriScore(produit, typeProduit);
        
        // IMPORTANT DEBUG LOG
        System.out.println("CALCULATED NUTRISCORE: " + nutriScore + " FOR PRODUCT: " + produit.getNom());
        
        // Définir le NutriScore et sa description
        produit.setValeurNutriScore(nutriScore);
        produit.setDescriptionNutriScore(generateNutriScoreDescription(nutriScore));
        
        // Sauvegarder le produit avec le NutriScore calculé
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> findAll() {
        return StreamSupport.stream(produitRepository
                .findAll()
                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Produit> findOnebyId(String id) {
        return produitRepository.findById(id);
    }

    @Override
    public Optional<Produit> findByCodeBarre(Long codeBarre) {
        System.out.println("Service findByCodeBarre received: " + codeBarre + " (Type: " + (codeBarre != null ? codeBarre.getClass().getSimpleName() : "null") + ")");
        try {
            // Add debugging for special barcode - check if the database has this specific product
            if (codeBarre != null && codeBarre == 6133414007137L) {
                System.out.println("PRIORITY BARCODE DETECTED: " + codeBarre);
                System.out.println("Checking if it exists in database directly...");
                // Force cast to check if we have any products with this exact value
                List<Produit> allProducts = produitRepository.findAll();
                System.out.println("Total products in DB: " + allProducts.size());
                boolean found = false;
                for (Produit p : allProducts) {
                    if (p.getCodeBarre() != null && p.getCodeBarre().toString().equals("6133414007137")) {
                        System.out.println("FOUND MATCHING PRODUCT: " + p.getNom() + ", ID: " + p.getId());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("NO MATCHING PRODUCT FOUND FOR 6133414007137");
                }
            }
            
            // Find product by Long barcode
            Optional<Produit> result = produitRepository.findByCodeBarre(codeBarre);
            System.out.println("Search result for barcode '" + codeBarre + "': " + (result.isPresent() ? "Found" : "Not found"));
            return result;
        } catch (Exception e) {
            System.out.println("Error in findByCodeBarre service: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean isExists(String id) {
        return produitRepository.existsById(id);
    }

    @Override
    public Produit partialUpdate(String id, Produit produit) {
        return produitRepository.findById(id)
                .map(existingProduit -> {
                    if (produit.getNom() != null) existingProduit.setNom(produit.getNom());
                    if (produit.getCodeBarre() != null) existingProduit.setCodeBarre(produit.getCodeBarre());
                    if (produit.getMarque() != null) existingProduit.setMarque(produit.getMarque());
                    if (produit.getCategorie() != null) existingProduit.setCategorie(produit.getCategorie());
                    if (produit.getDescription() != null) existingProduit.setDescription(produit.getDescription());
                    if (produit.getQuantite() != null) existingProduit.setQuantite(produit.getQuantite());
                    if (produit.getImageUrl() != null) existingProduit.setImageUrl(produit.getImageUrl());
                    if (produit.getIngredients() != null) existingProduit.setIngredients(produit.getIngredients());
                    if (produit.getNomAdditif() != null) existingProduit.setNomAdditif(produit.getNomAdditif());
                    return produitRepository.save(existingProduit);
                })
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit setPhotoUrl(String id, String photoUrl) {
        return produitRepository.findById(id)
                .map(produit -> {
                    produit.setImageUrl(photoUrl);
                    return produitRepository.save(produit);
                })
                .orElse(null);
    }

    @Override
    public Produit save(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> getProduitsByType(TypeProduit typeProduit) {
        return produitRepository.findAll().stream()
                .filter(produit -> {
                    // Calculer le NutriScore pour chaque produit
                    ValeurNutriScore nutriScore = nutriScoreService.calculateNutriScore(produit, typeProduit);
                    // Mettre à jour le NutriScore du produit
                    produit.setValeurNutriScore(nutriScore);
                    produit.setDescriptionNutriScore(generateNutriScoreDescription(nutriScore));
                    return true;
                })
                .collect(Collectors.toList());
    }
}
