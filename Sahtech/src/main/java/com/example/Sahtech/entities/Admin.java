package com.example.Sahtech.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin extends Utilisateurs{

    @Id
    private String id;
    
    private String role; // SUPER_ADMIN, CONTENT_ADMIN, USER_ADMIN, etc.
    private List<String> permissions;
    private String departement;
    private Map<String, Boolean> dashboardAccess; // Controls which dashboard sections this admin can access
    private List<String> nutritionistesGeres; // Nutritionists managed by this admin
    private List<String> issuesAssigned; // Issues assigned to this admin for review
    private Boolean canApproveProducts; // Permission to approve supplement product submissions
    private Boolean canManageUsers; // Permission to manage user accounts
    private Boolean canManageNutritionists; // Permission to manage nutritionist accounts

}
