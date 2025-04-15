package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.Ingrediants;
import com.example.Sahtech.repositories.IngrediantsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoTestConfig.class)
@ActiveProfiles("test")
public class IngrediantRepositoryIntegrationTests {

    private final IngrediantsRepository underTest;

    @Autowired
    public IngrediantRepositoryIntegrationTests(IngrediantsRepository underTest) {
        this.underTest=underTest;
    }

    @Test
    public void TestThatIngrediantsCanBeCreatedAndRecalled(){
        Ingrediants ingrediantA = TestDataUtil.createTestIngrediantsA();
        underTest.save(ingrediantA);
        Optional<Ingrediants> result = underTest.findById(ingrediantA.getIdIngrediant());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ingrediantA);
    }

    @Test
    public void TestThatMultipleIngerdiantsCanBeCreatedAndRecalled(){
        Ingrediants ingrediantA = TestDataUtil.createTestIngrediantsA();
         underTest.save(ingrediantA);
        Ingrediants ingrediantB = TestDataUtil.createTestIngrediantsB();
        underTest.save(ingrediantB);
        Ingrediants ingrediantC = TestDataUtil.createTestIngrediantsC();
        underTest.save(ingrediantC);

        Iterable<Ingrediants> result = underTest.findAll();
        assertThat(result).
                hasSize(3).
               containsExactly(ingrediantB, ingrediantC, ingrediantA);
    }


    @Test
    public void TestThatIngrediantsCanBeUpdated(){
        Ingrediants ingrediantA = TestDataUtil.createTestIngrediantsA();
        underTest.save(ingrediantA);
        ingrediantA.setNomIngrediant("UPDATED");
        underTest.save(ingrediantA);
        Optional<Ingrediants> result = underTest.findById(ingrediantA.getIdIngrediant());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ingrediantA);
    }


    @Test
    public void TestThatIngrediantsCanBeDeleted(){
        Ingrediants ingrediantA = TestDataUtil.createTestIngrediantsA();
        underTest.save(ingrediantA);
        underTest.deleteById(ingrediantA.getIdIngrediant());
        Optional<Ingrediants> result = underTest.findById(ingrediantA.getIdIngrediant());
        assertThat(result).isEmpty();
    }
}
