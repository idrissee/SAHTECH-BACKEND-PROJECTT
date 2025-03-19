package com.example.Sahtech.Controllers;


import com.example.Sahtech.entities.Admin;
import com.example.Sahtech.entities.Utilisateurs;
import com.example.Sahtech.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/API/Sahtech/Admin") // URL de base pour ce contrôleur
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/All")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> list = adminRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }



}
