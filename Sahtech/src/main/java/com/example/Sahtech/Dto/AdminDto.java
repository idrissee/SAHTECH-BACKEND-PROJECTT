package com.example.Sahtech.Dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {

    private String id;
    private String role;
    private List<String> permissions;
    private String departement;
    private Map<String, Boolean> dashboardAccess;
    private List<String> nutritionistesGeres;
    private List<String> issuesAssigned;
    private Boolean canApproveProducts;
    private Boolean canManageUsers;
    private Boolean canManageNutritionists;
}
