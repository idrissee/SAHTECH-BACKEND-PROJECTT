package com.example.Sahtech.services.Impl.ProduitDetaille;

import com.example.Sahtech.Enum.TypeProduit;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.NutriScoreService;
import com.example.Sahtech.Enum.ValeurNutriScore;
import org.springframework.stereotype.Service;

@Service
public class NutriScoreServiceImpl implements NutriScoreService {

    private double extractNumericValue(String quantite) {
        if (quantite == null || quantite.trim().isEmpty()) {
            return 0.0;
        }
        try {
            String numericPart = quantite.trim().replaceAll("[^0-9.]", "");
            if (numericPart.isEmpty()) {
                return 0.0;
            }
            return Double.parseDouble(numericPart);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private double getEnergieValue(Produit produit) {
        if (produit.getIngredients() == null) {
            return 0.0;
        }
        return produit.getIngredients().stream()
                .filter(ingredient -> ingredient.getNomIngrediant() != null && 
                           ingredient.getNomIngrediant().name().equals("ÉNERGIE_KCAL"))
                .findFirst()
                .map(ingredient -> extractNumericValue(ingredient.getQuantite()))
                .orElse(0.0);
    }

    private double getSucresValue(Produit produit) {
        return produit.getIngredients().stream()
                .filter(ingredient -> ingredient.getNomIngrediant() != null && 
                           ingredient.getNomIngrediant().name().equals("SUCRES"))
                .findFirst()
                .map(ingredient -> extractNumericValue(ingredient.getQuantite()))
                .orElse(0.0);
    }

    private double getAcidesGrasSaturesValue(Produit produit) {
        return produit.getIngredients().stream()
                .filter(ingredient -> ingredient.getNomIngrediant() != null && 
                           ingredient.getNomIngrediant().name().equals("ACIDES_GRAS_SATURÉS"))
                .findFirst()
                .map(ingredient -> extractNumericValue(ingredient.getQuantite()))
                .orElse(0.0);
    }

    private double getSodiumValue(Produit produit) {
        return produit.getIngredients().stream()
                .filter(ingredient -> ingredient.getNomIngrediant() != null && 
                           ingredient.getNomIngrediant().name().equals("SEL"))
                .findFirst()
                .map(ingredient -> {
                    try {
                        double value = extractNumericValue(ingredient.getQuantite());
                        return value * 400;
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                })
                .orElse(0.0);
    }

    private double getFibresValue(Produit produit) {
        return produit.getIngredients().stream()
                .filter(ingredient -> ingredient.getNomIngrediant() != null && 
                           ingredient.getNomIngrediant().name().equals("FIBRES_ALIMENTAIRES"))
                .findFirst()
                .map(ingredient -> extractNumericValue(ingredient.getQuantite()))
                .orElse(0.0);
    }

    private double getProteinesValue(Produit produit) {
        return produit.getIngredients().stream()
                .filter(ingredient -> ingredient.getNomIngrediant() != null && 
                           ingredient.getNomIngrediant().name().equals("PROTÉINES"))
                .findFirst()
                .map(ingredient -> extractNumericValue(ingredient.getQuantite()))
                .orElse(0.0);
    }

    private double getFruitsValue(Produit produit) {
        return produit.getIngredients().stream()
                .filter(ingredient -> ingredient.getNomIngrediant() != null && 
                           ingredient.getNomIngrediant().name().equals("FRUITS"))
                .findFirst()
                .map(ingredient -> extractNumericValue(ingredient.getQuantite()))
                .orElse(0.0);
    }

    private int calculateNegativePoints(double energie, double sucres, double acidesGrasSatures, double sodium, TypeProduit typeProduit) {
        int energiePoints = calculateEnergyPoints(energie, typeProduit);
        int sucresPoints = calculateSugarPoints(sucres, typeProduit);
        int acidesGrasPoints = calculateSaturatedFatPoints(acidesGrasSatures, typeProduit);
        int sodiumPoints = calculateSodiumPoints(sodium);

        return energiePoints + sucresPoints + acidesGrasPoints + sodiumPoints;
    }

    private int calculatePositivePoints(double fibres, double proteines, double fruits, TypeProduit typeProduit) {
        int fibrePoints = calculateFiberPoints(fibres);
        int proteinPoints = calculateProteinPoints(proteines);
        int fruitPoints = calculateFruitPoints(fruits, typeProduit);

        return fibrePoints + proteinPoints + fruitPoints;
    }

    private int calculateEnergyPoints(double energy, TypeProduit typeProduit) {
        if (typeProduit == TypeProduit.BOISSONS) {
            if (energy <= 0) return 0;
            if (energy <= 30) return 1;
            if (energy <= 60) return 2;
            if (energy <= 90) return 3;
            if (energy <= 120) return 4;
            if (energy <= 150) return 5;
            if (energy <= 180) return 6;
            if (energy <= 210) return 7;
            if (energy <= 240) return 8;
            if (energy <= 270) return 9;
            return 10;
        } else {
            if (energy <= 335) return 0;
            if (energy <= 670) return 1;
            if (energy <= 1005) return 2;
            if (energy <= 1340) return 3;
            if (energy <= 1675) return 4;
            if (energy <= 2010) return 5;
            if (energy <= 2345) return 6;
            if (energy <= 2680) return 7;
            if (energy <= 3015) return 8;
            if (energy <= 3350) return 9;
            return 10;
        }
    }

    private int calculateSugarPoints(double sugar, TypeProduit typeProduit) {
        if (typeProduit == TypeProduit.BOISSONS) {
            if (sugar <= 0) return 0;
            if (sugar <= 1.5) return 1;
            if (sugar <= 3) return 2;
            if (sugar <= 4.5) return 3;
            if (sugar <= 6) return 4;
            if (sugar <= 7.5) return 5;
            if (sugar <= 9) return 6;
            if (sugar <= 10.5) return 7;
            if (sugar <= 12) return 8;
            if (sugar <= 13.5) return 9;
            return 10;
        } else {
            if (sugar <= 4.5) return 0;
            if (sugar <= 9) return 1;
            if (sugar <= 13.5) return 2;
            if (sugar <= 18) return 3;
            if (sugar <= 22.5) return 4;
            if (sugar <= 27) return 5;
            if (sugar <= 31) return 6;
            if (sugar <= 36) return 7;
            if (sugar <= 40) return 8;
            if (sugar <= 45) return 9;
            return 10;
        }
    }

    private int calculateSaturatedFatPoints(double saturatedFat, TypeProduit typeProduit) {
        if (typeProduit == TypeProduit.MATIERES_GRASSES) {
            if (saturatedFat <= 10) return 0;
            if (saturatedFat <= 16) return 1;
            if (saturatedFat <= 22) return 2;
            if (saturatedFat <= 28) return 3;
            if (saturatedFat <= 34) return 4;
            if (saturatedFat <= 40) return 5;
            if (saturatedFat <= 46) return 6;
            if (saturatedFat <= 52) return 7;
            if (saturatedFat <= 58) return 8;
            if (saturatedFat <= 64) return 9;
            return 10;
        } else {
            if (saturatedFat <= 1) return 0;
            if (saturatedFat <= 2) return 1;
            if (saturatedFat <= 3) return 2;
            if (saturatedFat <= 4) return 3;
            if (saturatedFat <= 5) return 4;
            if (saturatedFat <= 6) return 5;
            if (saturatedFat <= 7) return 6;
            if (saturatedFat <= 8) return 7;
            if (saturatedFat <= 9) return 8;
            if (saturatedFat <= 10) return 9;
            return 10;
        }
    }

    private int calculateSodiumPoints(double sodium) {
        if (sodium <= 90) return 0;
        if (sodium <= 180) return 1;
        if (sodium <= 270) return 2;
        if (sodium <= 360) return 3;
        if (sodium <= 450) return 4;
        if (sodium <= 540) return 5;
        if (sodium <= 630) return 6;
        if (sodium <= 720) return 7;
        if (sodium <= 810) return 8;
        if (sodium <= 900) return 9;
        return 10;
    }

    private int calculateFiberPoints(double fiber) {
        if (fiber <= 0.9) return 0;
        if (fiber <= 1.9) return 1;
        if (fiber <= 2.8) return 2;
        if (fiber <= 3.7) return 3;
        if (fiber <= 4.7) return 4;
        return 5;
    }

    private int calculateProteinPoints(double protein) {
        if (protein <= 1.6) return 0;
        if (protein <= 3.2) return 1;
        if (protein <= 4.8) return 2;
        if (protein <= 6.4) return 3;
        if (protein <= 8) return 4;
        return 5;
    }

    private int calculateFruitPoints(double fruits, TypeProduit typeProduit) {
        if (typeProduit == TypeProduit.BOISSONS) {
            if (fruits <= 40) return 0;
            if (fruits <= 60) return 2;
            if (fruits <= 80) return 4;
            return 10;
        } else {
            if (fruits <= 40) return 0;
            if (fruits <= 60) return 1;
            if (fruits <= 80) return 2;
            return 5;
        }
    }

    private int calculateFinalScore(int negativePoints, int positivePoints, double fruits, TypeProduit typeProduit) {
        return negativePoints - positivePoints;
    }

    private ValeurNutriScore determineNutriScore(int score, TypeProduit typeProduit) {
        if (typeProduit == TypeProduit.BOISSONS) {
            if (score <= 1) return ValeurNutriScore.B;
            if (score <= 5) return ValeurNutriScore.C;
            if (score <= 9) return ValeurNutriScore.D;
            return ValeurNutriScore.E;
        } else {
            if (score <= -1) return ValeurNutriScore.A;
            if (score <= 2) return ValeurNutriScore.B;
            if (score <= 10) return ValeurNutriScore.C;
            if (score <= 18) return ValeurNutriScore.D;
            return ValeurNutriScore.E;
        }
    }

    @Override
    public ValeurNutriScore calculateNutriScore(Produit produit, TypeProduit typeProduit) {
        if (typeProduit == TypeProduit.EAU) {
            return ValeurNutriScore.A;
        }

        double energie = getEnergieValue(produit);
        double sucres = getSucresValue(produit);
        double acidesGrasSatures = getAcidesGrasSaturesValue(produit);
        double sodium = getSodiumValue(produit);
        double fibres = getFibresValue(produit);
        double proteines = getProteinesValue(produit);
        double fruits = getFruitsValue(produit);

        int negativePoints = calculateNegativePoints(energie, sucres, acidesGrasSatures, sodium, typeProduit);
        int positivePoints = calculatePositivePoints(fibres, proteines, fruits, typeProduit);
        
        int score = calculateFinalScore(negativePoints, positivePoints, fruits, typeProduit);
        
        return determineNutriScore(score, typeProduit);
    }
}
