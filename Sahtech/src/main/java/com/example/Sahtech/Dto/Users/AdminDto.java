package com.example.Sahtech.Dto.Users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class AdminDto extends UtilisateursDto {
    // Admin-specific fields can be added here
}
