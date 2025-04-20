package com.example.Sahtech.config;

import com.example.Sahtech.security.JwtTokenFilter;
import com.example.Sahtech.security.JwtTokenProvider;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
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
                .requestMatchers(HttpMethod.POST, "/localisations").hasAnyRole("ADMIN", "NUTRITIONIST")
                .requestMatchers(HttpMethod.GET, "/localisations/{id}").hasAnyRole("ADMIN", "NUTRITIONIST")
                .requestMatchers(HttpMethod.PUT, "/localisations/{id}").hasAnyRole("ADMIN", "NUTRITIONIST")
                
                // Pour toutes les autres opérations sur Nutritionnistes, seul Admin a accès
                .requestMatchers("/API/Sahtech/Nutrisionistes/**").hasRole("ADMIN")
                
                // Utilisateur : accès uniquement au GET et PUT de son propre profil
                .requestMatchers(HttpMethod.GET, "/API/Sahtech/Utilisateurs/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/API/Sahtech/Utilisateurs/{id}").hasAnyRole("ADMIN", "USER")
                
                // Historique de scan : utilisateur peut uniquement consulter son propre historique
                .requestMatchers(HttpMethod.GET, "/API/Sahtech/HistoriqueScan/utilisateur/{id}").hasAnyRole("ADMIN", "USER")

                
                // Pour toutes les autres opérations sur Utilisateurs, seul Admin a accès
                .requestMatchers("/API/Sahtech/Utilisateurs/**").hasRole("ADMIN")
                
                // Pour toutes les autres opérations sur HistoriqueScan, seul Admin a accès
                .requestMatchers("/API/Sahtech/HistoriqueScan/**").hasRole("ADMIN")
                
                // Toutes les autres APIs uniquement pour Admin (sauf celles spécifiées pour les nutritionnistes)
                .requestMatchers("/API/Sahtech/Ingredients/**").hasRole("ADMIN")
                .requestMatchers("/API/Sahtech/Additifs/**").hasRole("ADMIN")
                .requestMatchers("/localisations/**").hasRole("ADMIN") // Autres opérations sur localisations
                .requestMatchers("/API/Sahtech/Partenaires/**").hasRole("ADMIN")
                .requestMatchers("/API/Sahtech/Publicites/**").hasRole("ADMIN")
                .requestMatchers("/API/Sahtech/Produits/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            );

        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
} 