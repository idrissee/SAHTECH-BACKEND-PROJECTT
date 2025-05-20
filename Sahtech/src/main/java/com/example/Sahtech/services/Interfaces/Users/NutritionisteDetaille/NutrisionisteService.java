package com.example.Sahtech.services.Interfaces.Users.NutritionisteDetaille;



import com.example.Sahtech.entities.Users.NutritionisteDetaille.Nutrisioniste;

import java.util.List;

public interface NutrisionisteService {

    List<Nutrisioniste> getAllNutrisionistes();

    Nutrisioniste getNutrisionisteById(String id);

    Nutrisioniste getNutrisionisteByEmail(String email);

    Nutrisioniste getNutrisionisteByTelephone(String telephone);

    List<Nutrisioniste> getNutrisionistesBySpecialite(String specialite);

    Nutrisioniste createNutrisioniste(Nutrisioniste nutrisioniste);

    Nutrisioniste updateNutrisioniste(String id, Nutrisioniste nutrisioniste);

    boolean deleteNutrisioniste(String id);

    Nutrisioniste setPhotoUrl(String id, String photoUrl) ;

    Nutrisioniste setPhotoDiplome(String id, String photoUrl) ;

}
