package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoTestConfig.class)
@ActiveProfiles("test")
public class ProduitRepositoryIntegrationTests {

  private final ProduitRepository underTest;

  @Autowired
  public ProduitRepositoryIntegrationTests(ProduitRepository underTest) {
      this.underTest=underTest;
  }

  @BeforeEach
  void setUp() {
    // Clear the repository before each test
    underTest.deleteAll();
  }

  @Test
  @DisplayName("Test that a product can be created and retrieved")
  public void testThatProditCanBeCreatedAndRecalled() {
    // Arrange
    Produit produit = TestDataUtil.creatTestProduitA();
    
    // Act
    underTest.save(produit);
    Optional<Produit> result = underTest.findById(produit.getId());
    
    // Assert
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualToComparingFieldByField(produit);
  }

  @Test
  @DisplayName("Test that multiple products can be created and retrieved")
  public void testThatMultipleProduitsCanBeCreatedAndRecalled() {
    // Arrange
    Produit produitA = TestDataUtil.creatTestProduitA();
    underTest.save(produitA);
    Produit produitB = TestDataUtil.creatTestProduitB();
    underTest.save(produitB);
    Produit produitC = TestDataUtil.creatTestProduitC();
    underTest.save(produitC);

    // Act
    Iterable<Produit> result = underTest.findAll();
    
    // Assert - Check size without relying on specific order
    assertThat(result).hasSize(3);
    assertThat(result).contains(produitA, produitB, produitC);
  }

  @Test
  @DisplayName("Test that a product can be updated")
  public void testThatProduitCanBeUpdated(){
    // Arrange
    Produit produitA = TestDataUtil.creatTestProduitA();
    underTest.save(produitA);
    
    // Act
    produitA.setNom("bimoo");
    underTest.save(produitA);
    Optional<Produit> result = underTest.findById(produitA.getId());
    
    // Assert
    assertThat(result).isPresent();
    assertThat(result.get().getNom()).isEqualTo("bimoo");
    assertThat(result.get()).isEqualToComparingFieldByField(produitA);
  }

  @Test
  @DisplayName("Test that a product can be deleted")
  public void TestThatProduitCanBeDeleted(){
    // Arrange
    Produit produitA = TestDataUtil.creatTestProduitA();
    underTest.save(produitA);
    
    // Act
    underTest.deleteById(produitA.getId());
    Optional<Produit> result = underTest.findById(produitA.getId());
    
    // Assert
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Test that a product can be found by codeBarre")
  public void testThatProduitCanBeFoundByCodeBarre() {
    // Arrange
    Produit produit = TestDataUtil.creatTestProduitA();
    underTest.save(produit);
    
    // Act
    Optional<Produit> result = underTest.findByCodeBarre(produit.getCodeBarre());
    
    // Assert
    assertThat(result).isPresent();
    assertThat(result.get().getCodeBarre()).isEqualTo(produit.getCodeBarre());
  }
}
