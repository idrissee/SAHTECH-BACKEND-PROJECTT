package com.example.Sahtech.repositories.Pub;

import com.example.Sahtech.Enum.EtatPublicite;
import com.example.Sahtech.Enum.StatusPublicite;
import com.example.Sahtech.Enum.TypePublicite;
import com.example.Sahtech.entities.Pub.Publicite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface PubliciteRepository extends MongoRepository< Publicite,String> {
    List<Publicite> findByStatusPublicite(StatusPublicite statusPublicite);

    List<Publicite> findByEtatPublicite(EtatPublicite etatPublicite);

    List<Publicite> findByPartenaire_id(String partenaireId);

    List<Publicite> findByTypePub(TypePublicite typePub);

    List<Publicite> findByStatusPubliciteAndEtatPublicite(StatusPublicite statusPublicite, EtatPublicite etatPublicite);

    List<Publicite> findByDateDebutBeforeAndDateFinAfter(Date now, Date now1);
}
