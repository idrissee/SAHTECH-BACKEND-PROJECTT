package com.example.Sahtech.Repositpries;


import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.repositories.ProduitRepository;
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
      Optional<Produit> result = undertest.findById(produit.getIdProduit());
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
    produitA.setNomProduit("bimoo");
    undertest.save(produitA);
    Optional<Produit> result = undertest.findById(produitA.getIdProduit());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(produitA);
  }


  @Test
  public void TestThatProduitCanBeDeleted(){
    Produit produitA = TestDataUtil.creatTestProduitA();
    undertest.save(produitA);
    undertest.deleteById(produitA.getIdProduit());
    Optional<Produit> result = undertest.findById(produitA.getIdProduit());
    assertThat(result).isEmpty();
  }


}
