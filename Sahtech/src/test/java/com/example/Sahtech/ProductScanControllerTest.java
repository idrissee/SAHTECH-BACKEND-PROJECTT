package com.example.Sahtech;

import com.example.Sahtech.Controllers.ScanControllers.ProductScanController;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.services.Impl.DataValidationServiceImpl;
import com.example.Sahtech.services.Interfaces.DataValidationService;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.ProduitService;
import com.example.Sahtech.services.Interfaces.Users.UtilisateursService;
import com.example.Sahtech.services.Recommendation.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductScanControllerTest {

    @Mock
    private ProduitService produitService;

    @Mock
    private DataValidationService dataValidationService;
    
    @Mock
    private UtilisateursService utilisateursService;
    
    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private ProductScanController underTest;

    private Produit testProduit;
    private String testBarcode;
    private Long normalizedBarcode;

    @BeforeEach
    void setUp() {
        testProduit = TestDataUtil.createTestProduitComplete();
        testBarcode = "123-456-789"; // Barcode with non-numeric characters
        normalizedBarcode = 123456789L; // Expected normalized Long barcode
        
        // Setup barcode normalization behavior
        when(dataValidationService.normalizeBarcode(testBarcode)).thenReturn(normalizedBarcode);
    }

    @Test
    @DisplayName("scanProductByBarcode returns product when found")
    void scanProductByBarcode_ProductFound_ReturnsOkWithProduct() {
        // Arrange
        String userId = null; // No user ID for this test
        when(produitService.findByCodeBarre(normalizedBarcode)).thenReturn(Optional.of(testProduit));

        // Act
        ResponseEntity<?> response = underTest.scanProductByBarcode(testBarcode, userId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testProduit);
    }

    @Test
    @DisplayName("scanProductByBarcode returns not found when product doesn't exist")
    void scanProductByBarcode_ProductNotFound_ReturnsNotFound() {
        // Arrange
        String userId = null; // No user ID for this test
        when(produitService.findByCodeBarre(normalizedBarcode)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = underTest.scanProductByBarcode(testBarcode, userId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(Map.class);
        
        @SuppressWarnings("unchecked")
        Map<String, String> errorResponse = (Map<String, String>) response.getBody();
        assertThat(errorResponse.get("status")).isEqualTo("not_found");
        assertThat(errorResponse.get("message")).contains(normalizedBarcode.toString());
    }

    @Test
    @DisplayName("scanProductByBarcode handles exceptions")
    void scanProductByBarcode_Exception_ReturnsInternalServerError() {
        // Arrange
        String userId = null; // No user ID for this test
        when(produitService.findByCodeBarre(normalizedBarcode)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = underTest.scanProductByBarcode(testBarcode, userId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isInstanceOf(Map.class);
        
        @SuppressWarnings("unchecked")
        Map<String, String> errorResponse = (Map<String, String>) response.getBody();
        assertThat(errorResponse.get("status")).isEqualTo("error");
        assertThat(errorResponse.get("message")).contains("Error scanning product");
    }

    @Test
    @DisplayName("checkProductExists returns true when product exists")
    void checkProductExists_ProductExists_ReturnsTrueInMap() {
        // Arrange
        when(produitService.findByCodeBarre(normalizedBarcode)).thenReturn(Optional.of(testProduit));

        // Act
        ResponseEntity<?> response = underTest.checkProductExists(testBarcode);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(Map.class);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) response.getBody();
        assertThat(result.get("exists")).isEqualTo(true);
    }

    @Test
    @DisplayName("checkProductExists returns false when product doesn't exist")
    void checkProductExists_ProductDoesntExist_ReturnsFalseInMap() {
        // Arrange
        when(produitService.findByCodeBarre(normalizedBarcode)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = underTest.checkProductExists(testBarcode);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(Map.class);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) response.getBody();
        assertThat(result.get("exists")).isEqualTo(false);
    }

    @Test
    @DisplayName("checkProductExists handles exceptions")
    void checkProductExists_Exception_ReturnsInternalServerError() {
        // Arrange
        when(produitService.findByCodeBarre(normalizedBarcode)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = underTest.checkProductExists(testBarcode);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isInstanceOf(Map.class);
        
        @SuppressWarnings("unchecked")
        Map<String, String> errorResponse = (Map<String, String>) response.getBody();
        assertThat(errorResponse.get("status")).isEqualTo("error");
        assertThat(errorResponse.get("message")).contains("Error checking product");
    }

    @Test
    @DisplayName("Alternative endpoint checkProductExistsParam calls checkProductExists")
    void checkProductExistsParam_DelegatesCallToCheckProductExists() {
        // Arrange
        ProductScanController spyController = Mockito.spy(underTest);
        Mockito.doReturn(new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK))
               .when(spyController).checkProductExists(anyString());

        // Act
        spyController.checkProductExistsParam(testBarcode);

        // Assert
        Mockito.verify(spyController).checkProductExists(testBarcode);
    }

    @Test
    @DisplayName("Alternative endpoint getProductByParam calls scanProductByBarcode")
    void getProductByParam_DelegatesCallToScanProductByBarcode() {
        // Arrange
        ProductScanController spyController = Mockito.spy(underTest);
        Mockito.doReturn(new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK))
               .when(spyController).scanProductByBarcode(anyString(), Mockito.nullable(String.class));

        // Act
        spyController.getProductByParam(testBarcode);

        // Assert
        Mockito.verify(spyController).scanProductByBarcode(testBarcode, null);
    }
} 