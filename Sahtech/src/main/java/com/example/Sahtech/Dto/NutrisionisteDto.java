package com.example.Sahtech.Dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutrisionisteDto {

    private String id;
    private String specialite;
    private String description;
    private List<String> certifications;
    private Float tarifConsultation;
    private Boolean disponibiliteEnLigne;
    private String heuresDisponibilite;
    private Integer anneesExperience;
    private String etablissement;
    private Double noteEvaluation;
    private Integer nombreEvaluations;
    private List<String> patientsIds;
    private List<String> languesParlees;
}
