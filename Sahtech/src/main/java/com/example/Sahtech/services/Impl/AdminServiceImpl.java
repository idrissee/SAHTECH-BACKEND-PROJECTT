package com.example.Sahtech.services.Impl;

import com.example.Sahtech.entities.Admin;
import com.example.Sahtech.repositories.AdminRepository;
import com.example.Sahtech.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        Optional<Admin> existing = adminRepository.findById(id);
        if (existing.isPresent()) {
            updatedAdmin.setId(id);
            return adminRepository.save(updatedAdmin);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
