package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.entities.Additifs;
import com.example.Sahtech.repositories.AdditifsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdditifRepositoryIntegrationTests {

    private AdditifsRepository underTest;

    @Autowired
    public AdditifRepositoryIntegrationTests(AdditifsRepository underTest) {
        this.underTest=underTest;
    }

    @Test
    public void TestThatAdditifsCanBeCreatedAndRecalled(){
        Additifs additifsA = TestDataUtil.createTestAdditifsA();
        underTest.save(additifsA);
        Optional<Additifs> result = underTest.findById(additifsA.getIdAdditif());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(additifsA);
    }

    @Test
    public void TestThatMultipleAdditifsCanBeCreatedAndRecalled(){
        Additifs additifsA = TestDataUtil.createTestAdditifsA();
        underTest.save(additifsA);
        Additifs additifsB = TestDataUtil.createTestAdditifsB();
        underTest.save(additifsB);
        Additifs additifsC = TestDataUtil.createTestAdditifsC();
        underTest.save(additifsC);

        Iterable<Additifs> result = underTest.findAll();
        assertThat(result).
                hasSize(3).
                containsExactly(additifsB, additifsC, additifsA);
    }


    @Test
    public void TestThatAdditifsCanBeUpdated(){
        Additifs additifsA = TestDataUtil.createTestAdditifsA();
        underTest.save(additifsA);
        additifsA.setNomAdditif("UPDATED");
        underTest.save(additifsA);
        Optional<Additifs> result = underTest.findById(additifsA.getIdAdditif());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(additifsA);
    }


    @Test
    public void TestThatIngrediantsCanBeDeleted(){
       Additifs additifsA = TestDataUtil.createTestAdditifsA();
        underTest.save(additifsA);
        underTest.deleteById(additifsA.getIdAdditif());
        Optional<Additifs> result = underTest.findById(additifsA.getIdAdditif());
        assertThat(result).isEmpty();
    }
}
