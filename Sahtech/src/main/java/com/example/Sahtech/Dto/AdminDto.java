package com.example.Sahtech.Dto;


import com.example.Sahtech.entities.Personne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminDto extends Personne {

    private Long id;

}
