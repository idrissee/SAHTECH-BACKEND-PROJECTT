package com.example.Sahtech;

import com.example.Sahtech.Enum.*;
import com.example.Sahtech.Dto.ProduitDetaille.IngredientInfoDto;
import com.example.Sahtech.entities.ProduitDetaille.Additifs;
import com.example.Sahtech.entities.ProduitDetaille.Ingrediants;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Pub.Partenaire;
import com.example.Sahtech.entities.Pub.Publicite;
import com.example.Sahtech.entities.Scan.HistoriqueScan;
import com.example.Sahtech.entities.Users.Admin;
import com.example.Sahtech.entities.Users.NutritionisteDetaille.Localisation;
import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;
import com.example.Sahtech.entities.Users.Utilisateurs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDataUtil {

    public TestDataUtil() {}

    // Produit test data
    public static Produit creatTestProduitA() {
        return Produit.builder()
                .id("1L")
                .nom("rouiba")
                .categorie("Boisson")
                .codeBarre(12365877L)
                .build();
    }

    public static Produit creatTestProduitB() {
        return Produit.builder()
                .id("2L")
                .nom("Gateau")
                .categorie("Patisserie")
                .codeBarre(12348796L)
                .build();
    }

    public static Produit creatTestProduitC() {
        return Produit.builder()
                .id("3L")
                .nom("milka")
                .categorie("Chocolat")
                .codeBarre(1236987L)
                .build();
    }

    // More complete test product with all fields
    public static Produit createTestProduitComplete() {
        List<IngredientInfoDto> ingredients = new ArrayList<>();
        ingredients.add(new IngredientInfoDto(NomIngrediants.SUCRES, "10g"));
        ingredients.add(new IngredientInfoDto(NomIngrediants.SODIUM, "2g"));
        
        return Produit.builder()
                .id("4L")
                .nom("Produit Complet Test")
                .categorie("Test Category")
                .codeBarre(9876543210123L)
                .marque("Marque Test")
                .description("Description test produit")
                .quantite("500g")
                .imageUrl("https://example.com/image.jpg")
                .valeurNutriScore(ValeurNutriScore.B)
                .descriptionNutriScore("Bonne qualité nutritionnelle")
                .ingredients(ingredients)
                .nomAdditif(List.of("E100", "E202"))
                .build();
    }

    // Ingrediants test data
    public static Ingrediants createTestIngrediantsA() {
        return Ingrediants.builder()
                .idIngrediant("1L")
                .quantite("5.0g")
                .nomIngrediant(NomIngrediants.SODIUM)
                .produitId("1L")
                .build();
    }

    public static Ingrediants createTestIngrediantsB() {
        return Ingrediants.builder()
                .idIngrediant("2L")
                .quantite("4.0g")
                .nomIngrediant(NomIngrediants.SUCRES)
                .produitId("2L")
                .build();
    }

    public static Ingrediants createTestIngrediantsC() {
        return Ingrediants.builder()
                .idIngrediant("3L")
                .quantite("4.4g")
                .nomIngrediant(NomIngrediants.SODIUM)
                .produitId("3L")
                .build();
    }

    // Additifs test data
    public static Additifs createTestAdditifsA() {
       return Additifs.builder()
               .idAdditif("1L")
               .typeAdditif(TypeAdditif.Colorant)
               .seuil("niveau 2")
               .nomAdditif("sin1")
               .codeAdditif("E100")
               .toxicite("Faible")
               .build();
    }
    
    public static Additifs createTestAdditifsB() {
        return Additifs.builder()
                .idAdditif("2L")
                .typeAdditif(TypeAdditif.Conservateur)
                .seuil("niveau 3")
                .nomAdditif("sin2")
                .codeAdditif("E200")
                .toxicite("Moyenne")
                .build();
    }
    
    public static Additifs createTestAdditifsC() {
        return Additifs.builder()
                .idAdditif("3L")
                .typeAdditif(TypeAdditif.Édulcorant)
                .seuil("niveau 4")
                .nomAdditif("sin3")
                .codeAdditif("E300")
                .toxicite("Élevée")
                .build();
    }

    // Nutrisioniste test data
    public static Nutrisioniste createTestNutrisionisteA() {
        return Nutrisioniste.builder()
                .id("1L")
                .nom("Dupont")
                .prenom("Jean")
                .email("jean.dupont@example.com")
                .numTelephone(1234567890L)
                .specialite("Nutrition sportive")
                .dateDeNaissance(new Date())
                .password("password123")
                .estVerifie(true)
                .localisationId("1L")
                .build();
    }
    
    public static Nutrisioniste createTestNutrisionisteB() {
        return Nutrisioniste.builder()
                .id("2L")
                .nom("Martin")
                .prenom("Marie")
                .email("marie.martin@example.com")
                .numTelephone(9876543210L)
                .specialite("Diabète")
                .dateDeNaissance(new Date())
                .password("password456")
                .estVerifie(true)
                .localisationId("2L")
                .build();
    }
    
    public static Nutrisioniste createTestNutrisionisteC() {
        return Nutrisioniste.builder()
                .id("3L")
                .nom("Petit")
                .prenom("Pierre")
                .email("pierre.petit@example.com")
                .numTelephone(5555555555L)
                .specialite("Allergies alimentaires")
                .dateDeNaissance(new Date())
                .password("password789")
                .estVerifie(false)
                .localisationId("3L")
                .build();
    }

    // Admin test data
    public static Admin createTestAdminA() {
        return Admin.builder()
                .id("1L")
                .nom("Admin")
                .prenom("Super")
                .email("admin@example.com")
                .numTelephone(1111111111L)
                .password("adminpass123")
                .build();
    }
    
    public static Admin createTestAdminB() {
        return Admin.builder()
                .id("2L")
                .nom("Manager")
                .prenom("System")
                .email("manager@example.com")
                .numTelephone(2222222222L)
                .password("managerpass123")
                .build();
    }

    // Utilisateurs test data
    public static Utilisateurs createTestUtilisateurA() {
        return Utilisateurs.builder()
                .id("1L")
                .nom("Client")
                .prenom("Test")
                .email("client@example.com")
                .numTelephone(3333333333L)
                .password("userpass123")
                .dateDeNaissance(new Date())
                .taille(180.0F)
                .poids(75.0F)
                .sexe("M")
                .maladies(List.of(Maladie.MALADIE_CARDIAQUE))
                .objectif(Objectif.PERDRE_DU_POIDS)
                .build();
    }
    
    public static Utilisateurs createTestUtilisateurB() {
        return Utilisateurs.builder()
                .id("2L")
                .nom("User")
                .prenom("Regular")
                .email("user@example.com")
                .numTelephone(4444444444L)
                .password("userpass456")
                .dateDeNaissance(new Date())
                .taille(165.0F)
                .poids(60.0F)
                .sexe("F")
                .maladies(List.of(Maladie.ALLERGIE_ARACHIDES))
                .objectif(Objectif.REDUIRE_LA_TENSION_ARTERIELLE)
                .build();
    }

    // HistoriqueScan test data
    public static HistoriqueScan createTestHistoriqueScanA() {
        return HistoriqueScan.builder()
                .id("1")
                .utilisateur(createTestUtilisateurA())
                .produit(creatTestProduitA())
                .dateScan(LocalDateTime.now())
                .recommandationIA("Produit recommandé")
                .recommendationType("recommended")
                .build();
    }
    
    public static HistoriqueScan createTestHistoriqueScanB() {
        return HistoriqueScan.builder()
                .id("2")
                .utilisateur(createTestUtilisateurB())
                .produit(creatTestProduitB())
                .dateScan(LocalDateTime.now().minusDays(5))
                .recommandationIA("Consommation modérée conseillée")
                .recommendationType("caution")
                .build();
    }
    
    // Localisation test data
    public static Localisation createTestLocalisationA() {
        return Localisation.builder()
                .id("1L")
                .pays("France")
                .ville("Paris")
                .adresse("123 Rue de la Paix")
                .codePostal("75001")
                .latitude(48.8566)
                .longitude(2.3522)
                .build();
    }
    
    public static Localisation createTestLocalisationB() {
        return Localisation.builder()
                .id("2L")
                .pays("France")
                .ville("Lyon")
                .adresse("456 Avenue Berthelot")
                .codePostal("69007")
                .latitude(45.7578)
                .longitude(4.8320)
                .build();
    }
    
    public static Localisation createTestLocalisationC() {
        return Localisation.builder()
                .id("3L")
                .pays("France")
                .ville("Marseille")
                .adresse("789 Boulevard Michelet")
                .codePostal("13008")
                .latitude(43.2965)
                .longitude(5.3698)
                .build();
    }
    
    // Partenaire test data
    public static Partenaire createTestPartenaireA() {
        return Partenaire.builder()
                .id("1L")
                .nom("Bio Market")
                .description("Magasin de produits biologiques")
                .email("contact@biomarket.com")
                .telephone("0123456789")
                .domaineActivite("Distribution alimentaire")
                .siteWeb("www.biomarket.com")
                .statut(StatutPartenaire.EN_ATTENTE)
                .build();
    }
    
    public static Partenaire createTestPartenaireB() {
        return Partenaire.builder()
                .id("2L")
                .nom("Health & Co")
                .description("Chaîne de boutiques santé et bien-être")
                .email("info@healthco.com")
                .telephone("0123456780")
                .domaineActivite("Santé et bien-être")
                .siteWeb("www.healthco.com")
                .statut(StatutPartenaire.ACTIF)
                .build();
    }
    
    // Publicite test data
    public static Publicite createTestPubliciteA() {
        return Publicite.builder()
                .id("1LP")
                .titre("Offre spéciale Bio Market")
                .description("Profitez de 20% de réduction sur tous les produits bio")
                .typePub(TypePublicite.BANNIERE)
                .etatPublicite(EtatPublicite.DESACTIVEE)
                .statusPublicite(StatusPublicite.EN_ATTENTE)
                .partenaire_id("1L")
                .build();
    }
    
    public static Publicite createTestPubliciteB() {
        return Publicite.builder()
                .id("2L")
                .titre("Nouveaux produits santé")
                .description("Découvrez notre nouvelle gamme de produits santé")
                .typePub(TypePublicite.BANNIERE)
                .etatPublicite(EtatPublicite.PUBLIEE)
                .statusPublicite(StatusPublicite.EN_ATTENTE)
                .partenaire_id("2L")
                .build();
    }
}
