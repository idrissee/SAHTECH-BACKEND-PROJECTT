package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;

import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.Users.UtilisateursRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoTestConfig.class)
@ActiveProfiles("test")
public class UtilisateursRepositoryIntegrationTests {

    private final UtilisateursRepository underTest;

    @Autowired
    public UtilisateursRepositoryIntegrationTests(UtilisateursRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatUtilisateurCanBeCreatedAndRecalled() {
        Utilisateurs utilisateur = TestDataUtil.createTestUtilisateurA();
        underTest.save(utilisateur);
        Optional<Utilisateurs> result = underTest.findById(utilisateur.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(utilisateur);
    }

    @Test
    public void testThatMultipleUtilisateursCanBeCreatedAndRecalled() {
        Utilisateurs utilisateurA = TestDataUtil.createTestUtilisateurA();
        underTest.save(utilisateurA);
        Utilisateurs utilisateurB = TestDataUtil.createTestUtilisateurB();
        underTest.save(utilisateurB);

        Iterable<Utilisateurs> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(utilisateurA, utilisateurB);
    }

    @Test
    public void testThatUtilisateurCanBeUpdated() {
        Utilisateurs utilisateur = TestDataUtil.createTestUtilisateurA();
        underTest.save(utilisateur);
        utilisateur.setNom("Updated Name");
        underTest.save(utilisateur);
        Optional<Utilisateurs> result = underTest.findById(utilisateur.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(utilisateur);
        assertThat(result.get().getNom()).isEqualTo("Updated Name");
    }

    @Test
    public void testThatUtilisateurCanBeDeleted() {
        Utilisateurs utilisateur = TestDataUtil.createTestUtilisateurA();
        underTest.save(utilisateur);
        underTest.deleteById(utilisateur.getId());
        Optional<Utilisateurs> result = underTest.findById(utilisateur.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatUtilisateurCanBeFoundByEmail() {
        Utilisateurs utilisateur = TestDataUtil.createTestUtilisateurA();
        underTest.save(utilisateur);
        Optional<Utilisateurs> result = underTest.findByEmail(utilisateur.getEmail());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(utilisateur);
    }

    @Test
    public void testThatUtilisateursCanBeFoundByNom() {
        Utilisateurs utilisateurA = TestDataUtil.createTestUtilisateurA();
        underTest.save(utilisateurA);
        
        // Créer un autre utilisateur avec le même nom
        Utilisateurs utilisateurB = TestDataUtil.createTestUtilisateurB();
        utilisateurB.setNom(utilisateurA.getNom());
        underTest.save(utilisateurB);

        List<Utilisateurs> result = underTest.findByNom(utilisateurA.getNom());
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(utilisateurA, utilisateurB);
    }

    @Test
    public void testThatExistsByEmailReturnsTrue() {
        Utilisateurs utilisateur = TestDataUtil.createTestUtilisateurA();
        underTest.save(utilisateur);
        boolean exists = underTest.existsByEmail(utilisateur.getEmail());
        assertThat(exists).isTrue();
    }

    @Test
    public void testThatExistsByEmailReturnsFalse() {
        boolean exists = underTest.existsByEmail("nonexistent@example.com");
        assertThat(exists).isFalse();
    }
} 