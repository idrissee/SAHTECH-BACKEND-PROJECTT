package com.example.Sahtech.repositories;


import com.example.Sahtech.entities.Produit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProduitRepository extends CrudRepository<Produit, Long> {
}
