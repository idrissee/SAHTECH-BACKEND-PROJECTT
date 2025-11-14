package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.Scan.HistoriqueScan;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.entities.Users.Utilisateurs;
import com.example.Sahtech.repositories.Scan.HistoriqueScanRepository;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
import com.example.Sahtech.repositories.Users.UtilisateursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoTestConfig.class)
@ActiveProfiles("test")
public class HistoriqueScanRepositoryIntegrationTests {

    private final HistoriqueScanRepository underTest;
    private final UtilisateursRepository utilisateursRepository;
    private final ProduitRepository produitRepository;

    private Utilisateurs savedUtilisateurA;
    private Utilisateurs savedUtilisateurB;
    private Produit savedProduitA;
    private Produit savedProduitB;

    @Autowired
    public HistoriqueScanRepositoryIntegrationTests(
            HistoriqueScanRepository underTest,
            UtilisateursRepository utilisateursRepository,
            ProduitRepository produitRepository) {
        this.underTest = underTest;
        this.utilisateursRepository = utilisateursRepository;
        this.produitRepository = produitRepository;
    }

    @BeforeEach
    public void setup() {
        underTest.deleteAll();
        utilisateursRepository.deleteAll();
        produitRepository.deleteAll();

        savedUtilisateurA = utilisateursRepository.save(TestDataUtil.createTestUtilisateurA());
        savedUtilisateurB = utilisateursRepository.save(TestDataUtil.createTestUtilisateurB());
        savedProduitA = produitRepository.save(TestDataUtil.creatTestProduitA());
        savedProduitB = produitRepository.save(TestDataUtil.creatTestProduitB());
    }

    @Test
    public void testThatHistoriqueScanCanBeCreatedAndRecalled() {
        HistoriqueScan historiqueScan = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        underTest.save(historiqueScan);
        Optional<HistoriqueScan> result = underTest.findById(historiqueScan.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(historiqueScan.getId());
        assertThat(result.get().getUtilisateur().getId()).isEqualTo(savedUtilisateurA.getId());
        assertThat(result.get().getProduit().getId()).isEqualTo(savedProduitA.getId());
    }

    @Test
    public void testThatMultipleHistoriqueScanCanBeCreatedAndRecalled() {
        HistoriqueScan historiqueScanA = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        underTest.save(historiqueScanA);
        HistoriqueScan historiqueScanB = createTestHistoriqueScan(savedUtilisateurB, savedProduitB);
        historiqueScanB.setId("2");
        underTest.save(historiqueScanB);

        Iterable<HistoriqueScan> result = underTest.findAll();
        assertThat(result).hasSize(2);
        // Vérifier que les IDs des scans sont présents
        assertThat(result).extracting(HistoriqueScan::getId)
                .containsExactlyInAnyOrder(historiqueScanA.getId(), historiqueScanB.getId());
    }

    @Test
    public void testThatHistoriqueScanCanBeUpdated() {
        HistoriqueScan historiqueScan = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        underTest.save(historiqueScan);
        
        historiqueScan.setRecommendationType("avoid");
        historiqueScan.setRecommandationIA("Mise à jour de recommandation");
        underTest.save(historiqueScan);
        
        Optional<HistoriqueScan> result = underTest.findById(historiqueScan.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getRecommendationType()).isEqualTo("avoid");
        assertThat(result.get().getRecommandationIA()).isEqualTo("Mise à jour de recommandation");
    }

    @Test
    public void testThatHistoriqueScanCanBeDeleted() {
        HistoriqueScan historiqueScan = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        underTest.save(historiqueScan);
        underTest.deleteById(historiqueScan.getId());
        Optional<HistoriqueScan> result = underTest.findById(historiqueScan.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindByUtilisateurId() {
        HistoriqueScan historiqueScanA = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        underTest.save(historiqueScanA);
        HistoriqueScan historiqueScanB = createTestHistoriqueScan(savedUtilisateurA, savedProduitB);
        historiqueScanB.setId("2");
        underTest.save(historiqueScanB);
        HistoriqueScan historiqueScanC = createTestHistoriqueScan(savedUtilisateurB, savedProduitA);
        historiqueScanC.setId("3");
        underTest.save(historiqueScanC);

        List<HistoriqueScan> result = underTest.findByUtilisateurId(savedUtilisateurA.getId());
        assertThat(result).hasSize(2);
        assertThat(result).extracting(scan -> scan.getUtilisateur().getId())
                .containsOnly(savedUtilisateurA.getId());
    }

    @Test
    public void testFindByProduitId() {
        HistoriqueScan historiqueScanA = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        underTest.save(historiqueScanA);
        HistoriqueScan historiqueScanB = createTestHistoriqueScan(savedUtilisateurB, savedProduitA);
        historiqueScanB.setId("2");
        underTest.save(historiqueScanB);
        HistoriqueScan historiqueScanC = createTestHistoriqueScan(savedUtilisateurA, savedProduitB);
        historiqueScanC.setId("3");
        underTest.save(historiqueScanC);

        List<HistoriqueScan> result = underTest.findByProduitId(savedProduitA.getId());
        assertThat(result).hasSize(2);
        assertThat(result).extracting(scan -> scan.getProduit().getId())
                .containsOnly(savedProduitA.getId());
    }

    @Test
    public void testFindByRecommendationType() {
        HistoriqueScan historiqueScanA = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        historiqueScanA.setRecommendationType("recommended");
        underTest.save(historiqueScanA);
        HistoriqueScan historiqueScanB = createTestHistoriqueScan(savedUtilisateurB, savedProduitB);
        historiqueScanB.setId("2");
        historiqueScanB.setRecommendationType("recommended");
        underTest.save(historiqueScanB);
        HistoriqueScan historiqueScanC = createTestHistoriqueScan(savedUtilisateurA, savedProduitB);
        historiqueScanC.setId("3");
        historiqueScanC.setRecommendationType("caution");
        underTest.save(historiqueScanC);

        List<HistoriqueScan> result = underTest.findByRecommendationType("recommended");
        assertThat(result).hasSize(2);
        assertThat(result).extracting(HistoriqueScan::getRecommendationType)
                .containsOnly("recommended");
    }

    @Test
    public void testFindByUtilisateurIdAndDateScanAfter() {
        LocalDateTime now = LocalDateTime.now();
        
        HistoriqueScan historiqueScanA = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        historiqueScanA.setDateScan(now);
        underTest.save(historiqueScanA);
        
        HistoriqueScan historiqueScanB = createTestHistoriqueScan(savedUtilisateurA, savedProduitB);
        historiqueScanB.setId("2");
        historiqueScanB.setDateScan(now.minusDays(10));
        underTest.save(historiqueScanB);
        
        HistoriqueScan historiqueScanC = createTestHistoriqueScan(savedUtilisateurB, savedProduitA);
        historiqueScanC.setId("3");
        historiqueScanC.setDateScan(now);
        underTest.save(historiqueScanC);

        List<HistoriqueScan> result = underTest.findByUtilisateurIdAndDateScanAfter(
                savedUtilisateurA.getId(), now.minusDays(5));
        
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(historiqueScanA.getId());
    }

    private HistoriqueScan createTestHistoriqueScan(Utilisateurs utilisateur, Produit produit) {
        return HistoriqueScan.builder()
                .id("1")
                .utilisateur(utilisateur)
                .produit(produit)
                .dateScan(LocalDateTime.now())
                .recommandationIA("Produit recommandé")
                .recommendationType("recommended")
                .build();
    }
} 