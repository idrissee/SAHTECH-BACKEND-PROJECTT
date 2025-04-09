package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.HistoriqueScanDto;
import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.ProductScannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/scan")
public class ScanController {
    
    private final ProductScannerService productScannerService;
    private final Mapper<HistoriqueScan, HistoriqueScanDto> historiqueScanMapper;
    
    public ScanController(
            ProductScannerService productScannerService,
            Mapper<HistoriqueScan, HistoriqueScanDto> historiqueScanMapper) {
        this.productScannerService = productScannerService;
        this.historiqueScanMapper = historiqueScanMapper;
    }
    
    /**
     * Endpoint pour scanner un produit par code-barres
     * @param barcode Le code-barres du produit
     * @param userId L'identifiant de l'utilisateur qui scanne
     * @return Un DTO contenant les informations du scan et les recommandations
     */
    @PostMapping("/barcode")
    public ResponseEntity<HistoriqueScanDto> scanByBarcode(
            @RequestParam String barcode,
            @RequestParam Long userId) {
        HistoriqueScan scan = productScannerService.scanByBarcode(barcode, userId);
        return ResponseEntity.ok(historiqueScanMapper.mapTo(scan));
    }
    
    /**
     * Endpoint pour scanner un produit par image
     * @param image L'image du produit
     * @param userId L'identifiant de l'utilisateur qui scanne
     * @return Un DTO contenant les informations du scan et les recommandations
     */
    @PostMapping("/image")
    public ResponseEntity<HistoriqueScanDto> scanByImage(
            @RequestParam MultipartFile image,
            @RequestParam Long userId) {
        HistoriqueScan scan = productScannerService.scanByImage(image, userId);
        return ResponseEntity.ok(historiqueScanMapper.mapTo(scan));
    }
} 