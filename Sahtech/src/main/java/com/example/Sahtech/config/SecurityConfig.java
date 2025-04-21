package com.example.Sahtech.config;

import com.example.Sahtech.security.JwtTokenFilter;
import com.example.Sahtech.security.JwtTokenProvider;
import com.example.Sahtech.security.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // Allow all origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Allow all standard methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Allow standard headers
        configuration.setAllowCredentials(false); // No credentials for '*' origins
        configuration.setMaxAge(3600L); // 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Make main endpoint public
                .requestMatchers(HttpMethod.GET, "/API/Sahtech").permitAll()
                .requestMatchers(HttpMethod.GET, "/API/Sahtech/Publicites/**").permitAll()
                .requestMatchers("/API/Sahtech/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/API/Sahtech").permitAll()

                // Admin a accès à tout
                .requestMatchers("/API/Sahtech/Admins/**").hasRole("ADMIN")

                            // Pour accéder au compteur de scans d'un utilisateur (admin ou l'utilisateur lui-même)
                            .requestMatchers(HttpMethod.GET, "/API/Sahtech/Utilisateurs/{id}/scan-count").hasAnyRole("ADMIN", "USER")

                            // Pour accéder à tous les compteurs de scans (admin uniquement)
                            .requestMatchers(HttpMethod.GET, "/API/Sahtech/Utilisateurs/scan-counts").hasRole("ADMIN")

                    //nutritioniste
                    .requestMatchers(HttpMethod.GET ,"/API/Sahtech/Nutrisionistes/All").hasAnyRole("ADMIN","USER")

                    //Publicite
                    .requestMatchers(HttpMethod.GET ,"/API/Sahtech/Publicites/All" ).hasAnyRole("ADMIN","USER")

                    //HistoriqueScan
                    .requestMatchers(HttpMethod.POST,"/API/Sahtech/HistoriqueScan").hasAnyRole("ADMIN","USER")

                // Pour GET All Utilisateurs réservés aux admins
                .requestMatchers(HttpMethod.GET, "/API/Sahtech/Utilisateurs/All").hasRole("ADMIN")
                
                // Nutritionniste : accès uniquement au GET et PUT de son propre profil
                .requestMatchers(HttpMethod.GET, "/API/Sahtech/Nutrisionistes/{id}").hasAnyRole("ADMIN", "NUTRITIONIST")
                .requestMatchers(HttpMethod.PUT, "/API/Sahtech/Nutrisionistes/{id}").hasAnyRole("ADMIN", "NUTRITIONIST")
                
                // Accès pour Nutritionnistes aux APIs de localisation spécifiques
                .requestMatchers(HttpMethod.POST, "/API/Sahtech/localisations").hasAnyRole("ADMIN", "NUTRITIONIST")
                .requestMatchers(HttpMethod.GET, "/API/Sahtech/localisations/{id}").hasAnyRole("ADMIN", "NUTRITIONIST")
                .requestMatchers(HttpMethod.PUT, "/API/Sahtech/localisations/{id}").hasAnyRole("ADMIN", "NUTRITIONIST")

                // Pour toutes les autres opérations sur Nutritionnistes, seul Admin a accès
                .requestMatchers("/API/Sahtech/Nutrisionistes/**").hasRole("ADMIN")
                
                // Utilisateur : accès uniquement au GET et PUT de son propre profil
                .requestMatchers(HttpMethod.GET, "/API/Sahtech/Utilisateurs/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/API/Sahtech/Utilisateurs/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/API/Sahtech/Utilisateurs/{id}/uploadPhoto").hasAnyRole("ADMIN", "USER")

                // Historique de scan : utilisateur peut uniquement consulter son propre historique et statistiques
                .requestMatchers(HttpMethod.GET, "/API/Sahtech/HistoriqueScan/utilisateur/{id}").hasAnyRole("ADMIN", "USER")

                
                // Pour toutes les autres opérations sur Utilisateurs, seul Admin a accès
                .requestMatchers("/API/Sahtech/Utilisateurs/**").hasRole("ADMIN")
                
                // Pour toutes les autres opérations sur HistoriqueScan, seul Admin a accès
                .requestMatchers("/API/Sahtech/HistoriqueScan/**").hasRole("ADMIN")
                
                // Toutes les autres APIs uniquement pour Admin (sauf celles spécifiées pour les nutritionnistes)
                .requestMatchers("/API/Sahtech/Ingredients/**").hasRole("ADMIN")
                .requestMatchers("/API/Sahtech/Additifs/**").hasRole("ADMIN")
                .requestMatchers("/API/Sahtech/Localisations/**").hasRole("ADMIN")
                .requestMatchers("/API/Sahtech/Partenaires/**").hasRole("ADMIN")
                .requestMatchers("/API/Sahtech/Publicites/**").hasRole("ADMIN")
                .requestMatchers("/API/Sahtech/Produits/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            );

        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider, tokenBlacklistService), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
} 