package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.entities.Produit;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.HistoriqueScanRepository;
import com.example.Sahtech.repositories.ProduitRepository;
import com.example.Sahtech.repositories.UtilisateursRepository;
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
        // Sauvegarde des utilisateurs et produits pour les tests
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
        historiqueScanB.setId(2L);
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
        
        historiqueScan.setNoteNutriScore("B");
        historiqueScan.setCommentaireUtilisateur("Commentaire mis à jour");
        underTest.save(historiqueScan);
        
        Optional<HistoriqueScan> result = underTest.findById(historiqueScan.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getNoteNutriScore()).isEqualTo("B");
        assertThat(result.get().getCommentaireUtilisateur()).isEqualTo("Commentaire mis à jour");
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
        historiqueScanB.setId(2L);
        underTest.save(historiqueScanB);
        HistoriqueScan historiqueScanC = createTestHistoriqueScan(savedUtilisateurB, savedProduitA);
        historiqueScanC.setId(3L);
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
        historiqueScanB.setId(2L);
        underTest.save(historiqueScanB);
        HistoriqueScan historiqueScanC = createTestHistoriqueScan(savedUtilisateurA, savedProduitB);
        historiqueScanC.setId(3L);
        underTest.save(historiqueScanC);

        List<HistoriqueScan> result = underTest.findByProduitId(savedProduitA.getId());
        assertThat(result).hasSize(2);
        assertThat(result).extracting(scan -> scan.getProduit().getId())
                .containsOnly(savedProduitA.getId());
    }

    @Test
    public void testFindByNoteNutriScore() {
        HistoriqueScan historiqueScanA = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        historiqueScanA.setNoteNutriScore("A");
        underTest.save(historiqueScanA);
        HistoriqueScan historiqueScanB = createTestHistoriqueScan(savedUtilisateurB, savedProduitB);
        historiqueScanB.setId(2L);
        historiqueScanB.setNoteNutriScore("A");
        underTest.save(historiqueScanB);
        HistoriqueScan historiqueScanC = createTestHistoriqueScan(savedUtilisateurA, savedProduitB);
        historiqueScanC.setId(3L);
        historiqueScanC.setNoteNutriScore("B");
        underTest.save(historiqueScanC);

        List<HistoriqueScan> result = underTest.findByNoteNutriScore("A");
        assertThat(result).hasSize(2);
        assertThat(result).extracting(HistoriqueScan::getNoteNutriScore)
                .containsOnly("A");
    }

    @Test
    public void testFindByUtilisateurIdAndDateScanAfter() {
        LocalDateTime now = LocalDateTime.now();
        
        HistoriqueScan historiqueScanA = createTestHistoriqueScan(savedUtilisateurA, savedProduitA);
        historiqueScanA.setDateScan(now);
        underTest.save(historiqueScanA);
        
        HistoriqueScan historiqueScanB = createTestHistoriqueScan(savedUtilisateurA, savedProduitB);
        historiqueScanB.setId(2L);
        historiqueScanB.setDateScan(now.minusDays(10));
        underTest.save(historiqueScanB);
        
        HistoriqueScan historiqueScanC = createTestHistoriqueScan(savedUtilisateurB, savedProduitA);
        historiqueScanC.setId(3L);
        historiqueScanC.setDateScan(now);
        underTest.save(historiqueScanC);

        List<HistoriqueScan> result = underTest.findByUtilisateurIdAndDateScanAfter(
                savedUtilisateurA.getId(), now.minusDays(5));
        
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(historiqueScanA.getId());
    }

    private HistoriqueScan createTestHistoriqueScan(Utilisateurs utilisateur, Produit produit) {
        return HistoriqueScan.builder()
                .id(1L)
                .utilisateur(utilisateur)
                .produit(produit)
                .dateScan(LocalDateTime.now())
                .noteNutriScore("A")
                .recommandationIA("Produit recommandé")
                .additifsDetectes(List.of("E100", "E200"))
                .ingredients(List.of("Sucre", "Sel"))
                .pointsPositifs(List.of("Peu de sucre"))
                .pointsNegatifs(List.of("Contient des additifs"))
                .impactSante("Bonne")
                .commentaireUtilisateur("Très bon produit")
                .build();
    }
} 