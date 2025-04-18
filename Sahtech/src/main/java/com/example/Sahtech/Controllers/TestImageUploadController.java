package com.example.Sahtech.Controllers;

import com.example.Sahtech.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/API/Sahtech")
public class TestImageUploadController {

    private final ImageService imageService;

    @Autowired
    public TestImageUploadController(ImageService imageService) {
        this.imageService = imageService; // 👈 on utilise ce qui est injecté
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTestImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = imageService.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'upload : " + e.getMessage());
        }
    }
}
