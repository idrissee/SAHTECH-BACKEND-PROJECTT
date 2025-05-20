package com.example.Sahtech.services.Impl.ProduitDetaille;



import com.example.Sahtech.entities.ProduitDetaille.Produit;
import com.example.Sahtech.repositories.ProduitDetaille.AdditifsRepository;
import com.example.Sahtech.repositories.ProduitDetaille.ProduitRepository;
import com.example.Sahtech.services.Interfaces.ProduitDetaille.ProduitService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProduitServiceImpl implements ProduitService {

    ProduitRepository produitRepository;
    private final AdditifsRepository additifRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository, AdditifsRepository additifRepository) {
        this.produitRepository = produitRepository;
        this.additifRepository = additifRepository;
    }

    @Override
    public Produit createProduit(Produit produit) {
        Produit savedProduit = produitRepository.save(produit);
        return savedProduit;
    }

    @Override
    public List<Produit> findAll() {
        return StreamSupport.stream(produitRepository
                .findAll()
                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Produit> findOnebyId(String id) {
        return produitRepository.findById(id);
    }

    @Override
    public Optional<Produit> findByCodeBarre(Long codeBarre) {
        System.out.println("Service findByCodeBarre received: " + codeBarre + " (Type: " + (codeBarre != null ? codeBarre.getClass().getSimpleName() : "null") + ")");
        try {
            // Add debugging for special barcode - check if the database has this specific product
            if (codeBarre != null && codeBarre == 6133414007137L) {
                System.out.println("PRIORITY BARCODE DETECTED: " + codeBarre);
                System.out.println("Checking if it exists in database directly...");
                // Force cast to check if we have any products with this exact value
                List<Produit> allProducts = produitRepository.findAll();
                System.out.println("Total products in DB: " + allProducts.size());
                boolean found = false;
                for (Produit p : allProducts) {
                    if (p.getCodeBarre() != null && p.getCodeBarre().toString().equals("6133414007137")) {
                        System.out.println("FOUND MATCHING PRODUCT: " + p.getNom() + ", ID: " + p.getId());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("NO MATCHING PRODUCT FOUND FOR 6133414007137");
                }
            }
            
            // Find product by Long barcode
            Optional<Produit> result = produitRepository.findByCodeBarre(codeBarre);
            System.out.println("Search result for barcode '" + codeBarre + "': " + (result.isPresent() ? "Found" : "Not found"));
            return result;
        } catch (Exception e) {
            System.out.println("Error in findByCodeBarre service: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean isExists(String id) {
        return produitRepository.existsById(id);
    }

    @Override
    public Produit partialUpdate(String id, Produit produit) {

        produit.setId(id);

        return produitRepository.findById(id).map(exisitingProduit ->{
            Optional.ofNullable(produit.getNom()).ifPresent(exisitingProduit::setNom);
            Optional.ofNullable(produit.getDescription()).ifPresent(exisitingProduit::setDescription);
            return produitRepository.save(exisitingProduit);
        }).orElseThrow(() -> new RuntimeException("Produit not found"));


    }

    @Override
    public void delete(String id) {

        produitRepository.deleteById(id);
    }

    @Override
    public Produit save(Produit produit) {

        return  produitRepository.save(produit);
    }

    @Override
    public Produit setPhotoUrl(String id, String photoUrl) {
        Optional<Produit> produitOpt = produitRepository.findById(id);
        if (produitOpt.isPresent()) {
            Produit produit = produitOpt.get();
            produit.setImageUrl(photoUrl);
            return produitRepository.save(produit);
        }
        return null;
    }
}
