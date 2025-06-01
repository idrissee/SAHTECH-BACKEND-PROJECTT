package com.example.Sahtech.services.Interfaces.Image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String uploadImage(MultipartFile file, String folder) throws IOException;
}
