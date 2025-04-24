package com.example.Sahtech.Repositpries;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.config.MongoTestConfig;
import com.example.Sahtech.entities.Users.Admin;
import com.example.Sahtech.repositories.Users.AdminRepository;
import org.junit.jupiter.api.AfterEach;
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
public class AdminRepositoryIntegrationTests {

    private final AdminRepository underTest;

    @Autowired
    public AdminRepositoryIntegrationTests(AdminRepository underTest) {
        this.underTest = underTest;
    }

    @AfterEach
    public void cleanup() {
        underTest.deleteAll();
    }

    @Test
    public void testThatAdminCanBeCreatedAndRecalled() {
        Admin admin = TestDataUtil.createTestAdminA();
        underTest.save(admin);
        Optional<Admin> result = underTest.findById(admin.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(admin);
    }

    @Test
    public void testThatMultipleAdminsCanBeCreatedAndRecalled() {
        Admin adminA = TestDataUtil.createTestAdminA();
        underTest.save(adminA);
        Admin adminB = TestDataUtil.createTestAdminB();
        underTest.save(adminB);

        Iterable<Admin> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(adminA, adminB);
    }

    @Test
    public void testThatAdminCanBeUpdated() {
        Admin admin = TestDataUtil.createTestAdminA();
        underTest.save(admin);
        admin.setNom("Updated Admin");
        underTest.save(admin);
        Optional<Admin> result = underTest.findById(admin.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(admin);
        assertThat(result.get().getNom()).isEqualTo("Updated Admin");
    }

    @Test
    public void testThatAdminCanBeDeleted() {
        Admin admin = TestDataUtil.createTestAdminA();
        underTest.save(admin);
        underTest.deleteById(admin.getId());
        Optional<Admin> result = underTest.findById(admin.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatAdminCanBeFoundByEmail() {
        Admin admin = TestDataUtil.createTestAdminA();
        underTest.save(admin);
        Optional<Admin> result = underTest.findByEmail(admin.getEmail());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(admin);
    }

    @Test
    public void testThatExistsByEmailReturnsTrue() {
        Admin admin = TestDataUtil.createTestAdminA();
        underTest.save(admin);
        boolean exists = underTest.existsByEmail(admin.getEmail());
        assertThat(exists).isTrue();
    }

    @Test
    public void testThatExistsByEmailReturnsFalse() {
        boolean exists = underTest.existsByEmail("nonexistent@example.com");
        assertThat(exists).isFalse();
    }
} 