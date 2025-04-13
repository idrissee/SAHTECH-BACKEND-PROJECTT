package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.Localisation;
import com.example.Sahtech.repositories.LocalisationRepository;
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
public class LocalisationRepositoryIntegrationTests {

    private final LocalisationRepository underTest;

    @Autowired
    public LocalisationRepositoryIntegrationTests(LocalisationRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatLocalisationCanBeCreatedAndRecalled() {
        Localisation localisation = TestDataUtil.createTestLocalisationA();
        underTest.save(localisation);
        Optional<Localisation> result = underTest.findById(localisation.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(localisation);
    }

    @Test
    public void testThatMultipleLocalisationsCanBeCreatedAndRecalled() {
        Localisation localisationA = TestDataUtil.createTestLocalisationA();
        underTest.save(localisationA);
        Localisation localisationB = TestDataUtil.createTestLocalisationB();
        underTest.save(localisationB);
        Localisation localisationC = TestDataUtil.createTestLocalisationC();
        underTest.save(localisationC);

        Iterable<Localisation> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(localisationA, localisationB, localisationC);
    }

    @Test
    public void testThatLocalisationCanBeUpdated() {
        Localisation localisation = TestDataUtil.createTestLocalisationA();
        underTest.save(localisation);
        localisation.setVille("Lille");
        localisation.setAdresse("10 Rue Nationale");
        underTest.save(localisation);
        Optional<Localisation> result = underTest.findById(localisation.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(localisation);
        assertThat(result.get().getVille()).isEqualTo("Lille");
        assertThat(result.get().getAdresse()).isEqualTo("10 Rue Nationale");
    }

    @Test
    public void testThatLocalisationCanBeDeleted() {
        Localisation localisation = TestDataUtil.createTestLocalisationA();
        underTest.save(localisation);
        underTest.deleteById(localisation.getId());
        Optional<Localisation> result = underTest.findById(localisation.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatLocalisationsCanBeFoundByPays() {
        Localisation localisationA = TestDataUtil.createTestLocalisationA();
        underTest.save(localisationA);
        Localisation localisationB = TestDataUtil.createTestLocalisationB();
        underTest.save(localisationB);
        Localisation localisationC = TestDataUtil.createTestLocalisationC();
        // Changeons le pays pour tester la recherche
        localisationC.setPays("Belgique");
        underTest.save(localisationC);

        List<Localisation> result = underTest.findByPays("France");
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(localisationA, localisationB);
    }

    @Test
    public void testThatLocalisationsCanBeFoundByVille() {
        Localisation localisationA = TestDataUtil.createTestLocalisationA();
        underTest.save(localisationA);
        Localisation localisationB = TestDataUtil.createTestLocalisationB();
        underTest.save(localisationB);
        
        List<Localisation> result = underTest.findByVille("Paris");
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(localisationA);
    }

    @Test
    public void testThatLocalisationsCanBeFoundByCodePostal() {
        Localisation localisationA = TestDataUtil.createTestLocalisationA();
        underTest.save(localisationA);
        Localisation localisationB = TestDataUtil.createTestLocalisationB();
        underTest.save(localisationB);
        
        List<Localisation> result = underTest.findByCodePostal("75001");
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(localisationA);
    }
} 