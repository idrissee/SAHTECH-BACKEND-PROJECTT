package com.example.Sahtech.services;

import com.example.Sahtech.entities.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(String id);
    Admin getAdminByEmail(String email);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(String id, Admin admin);
    boolean deleteAdmin(String id);

    boolean isExists(String id);
}
