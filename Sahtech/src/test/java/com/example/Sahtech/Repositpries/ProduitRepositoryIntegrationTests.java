package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
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

  private final ProduitRepository undertest;

  @Autowired
  public ProduitRepositoryIntegrationTests(ProduitRepository undertest) {
      this.undertest=undertest;
  }



    @Test
    public void testThatProditCanBeCreatedAndRecalled() {
      Produit produit = TestDataUtil.creatTestProduitA();
      undertest.save(produit);
      Optional<Produit> result = undertest.findById(produit.getId());
      assertThat(result).isPresent();
      assertThat(result.get()).isEqualTo(produit);
    }

  @Test
  public void testThatMultipleProduitsCanBeCreatedAndRecalled() {
    Produit produitA = TestDataUtil.creatTestProduitA();
    undertest.save(produitA);
    Produit produitB = TestDataUtil.creatTestProduitB();
    undertest.save(produitB);
    Produit produitC = TestDataUtil.creatTestProduitC();
    undertest.save(produitC);

    Iterable<Produit> result = undertest.findAll();
    assertThat(result)
            .hasSize(3).
            containsExactly(produitB, produitC, produitA);
  }

  @Test
  public void testThatProduitCanBeUpdated(){
    Produit produitA = TestDataUtil.creatTestProduitA();
    undertest.save(produitA);
    produitA.setNom("bimoo");
    undertest.save(produitA);
    Optional<Produit> result = undertest.findById(produitA.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(produitA);
  }


  @Test
  public void TestThatProduitCanBeDeleted(){
    Produit produitA = TestDataUtil.creatTestProduitA();
    undertest.save(produitA);
    undertest.deleteById(produitA.getId());
    Optional<Produit> result = undertest.findById(produitA.getId());
    assertThat(result).isEmpty();
  }


}
