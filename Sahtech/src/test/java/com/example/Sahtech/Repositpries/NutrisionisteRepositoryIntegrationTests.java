package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;
import com.example.Sahtech.repositories.Users.NutritionisteDetaille.NutritionisteRepository;
import org.junit.jupiter.api.AfterEach;
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
public class NutrisionisteRepositoryIntegrationTests {

    private final NutritionisteRepository underTest;

    @Autowired
    public NutrisionisteRepositoryIntegrationTests(NutritionisteRepository underTest) {
        this.underTest = underTest;
    }

    @AfterEach
    public void cleanup() {
        underTest.deleteAll();
    }

    @Test
    public void testThatNutrisionisteCanBeCreatedAndRecalled() {
        Nutrisioniste nutritioniste = TestDataUtil.createTestNutrisionisteA();
        underTest.save(nutritioniste);
        Optional<Nutrisioniste> result = underTest.findById(nutritioniste.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(nutritioniste);
    }

    @Test
    public void testThatMultipleNutrisionistesCanBeCreatedAndRecalled() {
        Nutrisioniste nutrisionisteA = TestDataUtil.createTestNutrisionisteA();
        underTest.save(nutrisionisteA);
        Nutrisioniste nutrisionisteB = TestDataUtil.createTestNutrisionisteB();
        underTest.save(nutrisionisteB);
        Nutrisioniste nutrisionisteC = TestDataUtil.createTestNutrisionisteC();
        underTest.save(nutrisionisteC);

        Iterable<Nutrisioniste> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(nutrisionisteA, nutrisionisteB, nutrisionisteC);
    }

    @Test
    public void testThatNutrisionisteCanBeUpdated() {
        Nutrisioniste nutritioniste = TestDataUtil.createTestNutrisionisteA();
        underTest.save(nutritioniste);
        nutritioniste.setSpecialite("Nutrition pédiatrique");
        underTest.save(nutritioniste);
        Optional<Nutrisioniste> result = underTest.findById(nutritioniste.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(nutritioniste);
        assertThat(result.get().getSpecialite()).isEqualTo("Nutrition pédiatrique");
    }

    @Test
    public void testThatNutrisionisteCanBeDeleted() {
        Nutrisioniste nutritioniste = TestDataUtil.createTestNutrisionisteA();
        underTest.save(nutritioniste);
        underTest.deleteById(nutritioniste.getId());
        Optional<Nutrisioniste> result = underTest.findById(nutritioniste.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatNutrisionisteCanBeFoundByEmail() {
        Nutrisioniste nutritioniste = TestDataUtil.createTestNutrisionisteA();
        underTest.save(nutritioniste);
        Optional<Nutrisioniste> result = underTest.findByEmail(nutritioniste.getEmail());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(nutritioniste);
    }

    @Test
    public void testThatNutrisionistesCanBeFoundBySpecialite() {
        Nutrisioniste nutrisionisteA = TestDataUtil.createTestNutrisionisteA();
        underTest.save(nutrisionisteA);
        
        Nutrisioniste nutrisionisteB = TestDataUtil.createTestNutrisionisteB();
        underTest.save(nutrisionisteB);
        
        Nutrisioniste nutrisionisteC = TestDataUtil.createTestNutrisionisteC();
        // Même spécialité que nutrisionisteA pour tester la recherche
        nutrisionisteC.setSpecialite(nutrisionisteA.getSpecialite());
        underTest.save(nutrisionisteC);

        List<Nutrisioniste> result = underTest.findBySpecialite(nutrisionisteA.getSpecialite());
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(nutrisionisteA, nutrisionisteC);
    }

    @Test
    public void testThatNutrisionistesCanBeFoundByEstVerifie() {
        Nutrisioniste nutrisionisteA = TestDataUtil.createTestNutrisionisteA(); // estVerifie = true
        underTest.save(nutrisionisteA);
        
        Nutrisioniste nutrisionisteB = TestDataUtil.createTestNutrisionisteB(); // estVerifie = true 
        underTest.save(nutrisionisteB);
        
        Nutrisioniste nutrisionisteC = TestDataUtil.createTestNutrisionisteC(); // estVerifie = false
        underTest.save(nutrisionisteC);

        List<Nutrisioniste> result = underTest.findByEstVerifie(true);
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(nutrisionisteA, nutrisionisteB);
        
        List<Nutrisioniste> resultFalse = underTest.findByEstVerifie(false);
        assertThat(resultFalse).hasSize(1);
        assertThat(resultFalse).containsExactly(nutrisionisteC);
    }
} 