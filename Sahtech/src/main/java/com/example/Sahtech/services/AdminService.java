package com.example.Sahtech.services;

import com.example.Sahtech.entities.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Long id);
    Admin getAdminByEmail(String email);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Long id, Admin admin);
    boolean deleteAdmin(Long id);
}
