package com.example.Sahtech.services.Impl;

import com.example.Sahtech.TestDataUtil;
import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.services.Interfaces.DataValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataValidationServiceImplTest {

    private DataValidationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new DataValidationServiceImpl();
    }

    @Test
    @DisplayName("validateProduit returns true for valid product")
    void validateProduit_ValidProduct_ReturnsTrue() {
        // Arrange
        Produit validProduct = TestDataUtil.creatTestProduitA();
        
        // Act
        boolean result = underTest.validateProduit(validProduct);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("validateProduit returns false for null product")
    void validateProduit_NullProduct_ReturnsFalse() {
        // Arrange
        Produit nullProduct = null;
        
        // Act
        boolean result = underTest.validateProduit(nullProduct);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("validateProduit returns false for product with null barcode")
    void validateProduit_NullBarcode_ReturnsFalse() {
        // Arrange
        Produit productWithNullBarcode = TestDataUtil.creatTestProduitA();
        productWithNullBarcode.setCodeBarre(null);
        
        // Act
        boolean result = underTest.validateProduit(productWithNullBarcode);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("validateProduit returns false for product with empty barcode")
    void validateProduit_EmptyBarcode_ReturnsFalse() {
        // Test removed since Produit.codeBarre is now Long type which cannot be empty, only null
    }

    @Test
    @DisplayName("validateProduit returns false for product with null name")
    void validateProduit_NullName_ReturnsFalse() {
        // Arrange
        Produit productWithNullName = TestDataUtil.creatTestProduitA();
        productWithNullName.setNom(null);
        
        // Act
        boolean result = underTest.validateProduit(productWithNullName);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("validateProduit returns false for product with empty name")
    void validateProduit_EmptyName_ReturnsFalse() {
        // Arrange
        Produit productWithEmptyName = TestDataUtil.creatTestProduitA();
        productWithEmptyName.setNom("");
        
        // Act
        boolean result = underTest.validateProduit(productWithEmptyName);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("fromFlutterFormat converts Flutter data to Produit correctly")
    void fromFlutterFormat_ValidData_ConvertsCorrectly() {
        // Arrange
        Map<String, Object> flutterData = new HashMap<>();
        flutterData.put("id", "test-id-123");
        flutterData.put("name", "Test Product");
        flutterData.put("barcode", "9876543210"); // String barcode will be converted to Long
        flutterData.put("brand", "Test Brand");
        flutterData.put("category", "Test Category");
        flutterData.put("description", "Test Description");
        flutterData.put("imageUrl", "https://example.com/image.jpg");
        
        // Act
        Produit result = underTest.fromFlutterFormat(flutterData);
        
        // Assert
        assertThat(result).isNotNull();
        assertEquals("test-id-123", result.getId());
        assertEquals("Test Product", result.getNom());
        assertEquals(9876543210L, result.getCodeBarre()); // Assert Long value
        assertEquals("Test Brand", result.getMarque());
        assertEquals("Test Category", result.getCategorie());
        assertEquals("Test Description", result.getDescription());
        assertEquals("https://example.com/image.jpg", result.getImageUrl());
    }

    @Test
    @DisplayName("fromFlutterFormat handles null input")
    void fromFlutterFormat_NullInput_ReturnsNull() {
        // Act
        Produit result = underTest.fromFlutterFormat(null);
        
        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("fromFlutterFormat uses fallback field names")
    void fromFlutterFormat_FallbackFields_ConvertsCorrectly() {
        // Arrange
        Map<String, Object> flutterData = new HashMap<>();
        flutterData.put("id", "test-id-123");
        flutterData.put("nom", "Test Product"); // Using nom instead of name
        flutterData.put("codeBarre", "9876543210"); // Using codeBarre instead of barcode
        flutterData.put("marque", "Test Brand"); // Using marque instead of brand
        flutterData.put("categorie", "Test Category"); // Using categorie instead of category
        flutterData.put("image", "https://example.com/image.jpg"); // Using image instead of imageUrl
        
        // Act
        Produit result = underTest.fromFlutterFormat(flutterData);
        
        // Assert
        assertThat(result).isNotNull();
        assertEquals("test-id-123", result.getId());
        assertEquals("Test Product", result.getNom());
        assertEquals(9876543210L, result.getCodeBarre()); // Assert Long value
        assertEquals("Test Brand", result.getMarque());
        assertEquals("Test Category", result.getCategorie());
        assertEquals("https://example.com/image.jpg", result.getImageUrl());
    }

    @Test
    @DisplayName("toFastApiFormat converts Produit to FastAPI format correctly")
    void toFastApiFormat_ValidProduct_ConvertsCorrectly() {
        // Arrange
        Produit product = TestDataUtil.createTestProduitComplete();
        
        // Act
        Map<String, Object> result = underTest.toFastApiFormat(product);
        
        // Assert
        assertThat(result).isNotNull();
        assertEquals(product.getId(), result.get("id"));
        assertEquals(product.getNom(), result.get("name"));
        assertEquals(product.getCodeBarre(), result.get("barcode")); // This should be Long
        assertEquals(product.getMarque(), result.get("brand"));
        assertEquals(product.getCategorie(), result.get("category"));
        assertEquals(product.getCategorie(), result.get("type"));
        assertEquals(product.getDescription(), result.get("description"));
        assertNotNull(result.get("ingredients"));
        assertNotNull(result.get("additives"));
        assertNotNull(result.get("nutrition_values"));
    }

    @Test
    @DisplayName("toFastApiFormat handles null input")
    void toFastApiFormat_NullInput_ReturnsNull() {
        // Act
        Map<String, Object> result = underTest.toFastApiFormat(null);
        
        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("normalizeBarcode converts numeric barcode to Long")
    void normalizeBarcode_NumericBarcode_ConvertsToLong() {
        // Arrange
        Long numericBarcode = 9876543210L;
        
        // Act
        Long result = underTest.normalizeBarcode(numericBarcode);
        
        // Assert
        assertEquals(numericBarcode, result);
    }
    
    @Test
    @DisplayName("normalizeBarcode converts String barcode to Long")
    void normalizeBarcode_StringBarcode_ConvertsToLong() {
        // Arrange
        String stringBarcode = "9876543210";
        
        // Act
        Long result = underTest.normalizeBarcode(stringBarcode);
        
        // Assert
        assertEquals(9876543210L, result);
    }
    
    @Test
    @DisplayName("normalizeBarcode removes non-numeric characters")
    void normalizeBarcode_BarcodeWithSpecialChars_RemovesSpecialChars() {
        // Arrange
        String messyBarcode = "987-654-3210";
        
        // Act
        Long result = underTest.normalizeBarcode(messyBarcode);
        
        // Assert
        assertEquals(9876543210L, result);
    }
    
    @Test
    @DisplayName("normalizeBarcode handles null input")
    void normalizeBarcode_NullInput_ReturnsNull() {
        // Act
        Long result = underTest.normalizeBarcode(null);
        
        // Assert
        assertNull(result);
    }
    
    @Test
    @DisplayName("normalizeBarcode handles empty string")
    void normalizeBarcode_EmptyString_ReturnsNull() {
        // Act
        Long result = underTest.normalizeBarcode("");
        
        // Assert
        assertNull(result);
    }
} 