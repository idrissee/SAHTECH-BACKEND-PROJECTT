package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.Partenaire;
import com.example.Sahtech.repositories.PartenaireRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoTestConfig.class)
@ActiveProfiles("test")
public class PartenaireRepositoryIntegrationTests {

    private final PartenaireRepository underTest;

    @Autowired
    public PartenaireRepositoryIntegrationTests(PartenaireRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatPartenaireCanBeCreatedAndRecalled() {
        Partenaire partenaire = TestDataUtil.createTestPartenaireA();
        underTest.save(partenaire);
        Optional<Partenaire> result = underTest.findById(partenaire.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(partenaire);
    }

    @Test
    public void testThatMultiplePartenairesCanBeCreatedAndRecalled() {
        Partenaire partenaireA = TestDataUtil.createTestPartenaireA();
        underTest.save(partenaireA);
        Partenaire partenaireB = TestDataUtil.createTestPartenaireB();
        underTest.save(partenaireB);

        Iterable<Partenaire> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(partenaireA, partenaireB);
    }

    @Test
    public void testThatPartenaireCanBeUpdated() {
        Partenaire partenaire = TestDataUtil.createTestPartenaireA();
        underTest.save(partenaire);
        partenaire.setNom("Nouveau Partenaire");
        partenaire.setDescription("Nouvelle description du partenaire");
        underTest.save(partenaire);
        Optional<Partenaire> result = underTest.findById(partenaire.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(partenaire);
        assertThat(result.get().getNom()).isEqualTo("Nouveau Partenaire");
        assertThat(result.get().getDescription()).isEqualTo("Nouvelle description du partenaire");
    }

    @Test
    public void testThatPartenaireCanBeDeleted() {
        Partenaire partenaire = TestDataUtil.createTestPartenaireA();
        underTest.save(partenaire);
        underTest.deleteById(partenaire.getId());
        Optional<Partenaire> result = underTest.findById(partenaire.getId());
        assertThat(result).isEmpty();
    }




} 