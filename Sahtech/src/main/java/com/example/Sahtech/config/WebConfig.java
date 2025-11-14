package com.example.Sahtech.config;

import com.example.Sahtech.Enum.ValeurNutriScore;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToValeurNutriScoreConverter());
    }
    
    private static class StringToValeurNutriScoreConverter implements Converter<String, ValeurNutriScore> {
        @Override
        public ValeurNutriScore convert(String source) {
            try {
                return ValeurNutriScore.valueOf(source.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Gérer le cas où la valeur n'est pas une valeur valide de l'énumération
                throw new IllegalArgumentException("Valeur de NutriScore invalide: " + source + 
                        ". Les valeurs acceptées sont: A, B, C, D, E");
            }
        }
    }
} 