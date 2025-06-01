package com.example.Sahtech.Enum;

/**
 * Enumération des interprétations de l'IMC selon les standards de l'OMS
 */
public enum InterpretationIMC {
    INSUFFISANCE_PONDERALE("Insuffisance pondérale (maigreur)"),
    CORPULENCE_NORMALE("Corpulence normale"),
    SURPOIDS("Surpoids"),
    OBESITE_MODEREE("Obésité modérée"),
    OBESITE_SEVERE("Obésité sévère"),
    OBESITE_MORBIDE("Obésité morbide ou massive");

    private final String description;

    InterpretationIMC(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Détermine l'interprétation de l'IMC en fonction de sa valeur numérique
     * @param imc La valeur de l'IMC
     * @return L'interprétation correspondante
     */
    public static InterpretationIMC getInterpretation(Float imc) {
        if (imc == null) {
            return null;
        }
        
        if (imc < 18.5) {
            return INSUFFISANCE_PONDERALE;
        } else if (imc < 25) {
            return CORPULENCE_NORMALE;
        } else if (imc < 30) {
            return SURPOIDS;
        } else if (imc < 35) {
            return OBESITE_MODEREE;
        } else if (imc < 40) {
            return OBESITE_SEVERE;
        } else {
            return OBESITE_MORBIDE;
        }
    }
} 