package com.example.Sahtech.Dto.Users.NutritionisteDetaille;

import com.example.Sahtech.Dto.Users.UtilisateursDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class NutrisionisteDto extends UtilisateursDto {
    private String specialite;
    private String localisationId;
    private Boolean estVerifie;
}
