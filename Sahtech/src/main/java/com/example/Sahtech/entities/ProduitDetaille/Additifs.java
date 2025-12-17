package com.example.Sahtech.entities.ProduitDetaille;


import com.example.Sahtech.Enum.TypeAdditif;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "additifs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class Additifs {

    @Id
    private String idAdditif;

    private String codeAdditif;
    private String nomAdditif;
    private TypeAdditif typeAdditif;
    private String seuil;
    private String toxicite;


}