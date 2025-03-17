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

  private ProduitRepository undertest;

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


}
