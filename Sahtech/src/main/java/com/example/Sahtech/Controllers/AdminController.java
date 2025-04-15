package com.example.Sahtech.Controllers;

import com.example.Sahtech.Dto.AdditifsDto;
import com.example.Sahtech.Dto.AdminDto;
import com.example.Sahtech.entities.Additifs;
import com.example.Sahtech.entities.Admin;
import com.example.Sahtech.mappers.Mapper;
import com.example.Sahtech.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/Sahtech/Admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private Mapper<Admin, AdminDto> adminMapper;

    // GET ALL ADMINS
    @GetMapping("/All")
    public List<AdminDto> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return admins.stream()
                .map(adminMapper::mapTo)
                .collect(Collectors.toList());
    }

    // GET ADMIN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable String id) {
        Admin admin = adminService.getAdminById(id);
        if (admin != null) {
            return new ResponseEntity<>(adminMapper.mapTo(admin), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET ADMIN BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<AdminDto> getAdminByEmail(@RequestParam String email) {
        Admin admin = adminService.getAdminByEmail(email);
        if (admin != null) {
            return new ResponseEntity<>(adminMapper.mapTo(admin), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE ADMIN
    @PostMapping
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        Admin admin = adminMapper.mapFrom(adminDto);
        Admin savedAdmin = adminService.createAdmin(admin);
        return new ResponseEntity<>(adminMapper.mapTo(savedAdmin), HttpStatus.CREATED);
    }

    // UPDATE ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable String id, @RequestBody AdminDto adminDto) {
        if(!adminService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adminDto.setId(id);
        Admin admin = adminMapper.mapFrom(adminDto);
        Admin updated = adminService.updateAdmin(id, admin);
        if (updated != null) {
            return new ResponseEntity<>(
                    adminMapper.mapTo(updated),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String id) {
        boolean deleted = adminService.deleteAdmin(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
