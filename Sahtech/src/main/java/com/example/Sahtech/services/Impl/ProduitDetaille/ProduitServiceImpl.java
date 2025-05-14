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
    public Optional<Produit> findByCodeBarre(String codeBarre) {
        System.out.println("Service findByCodeBarre received: " + codeBarre + " (Type: " + codeBarre.getClass().getSimpleName() + ")");
        try {
            // Try to find with string barcode directly first
            Optional<Produit> result = produitRepository.findByCodeBarre(codeBarre);
            
            // If not found, try parsing as Long
            if (result.isEmpty()) {
                try {
                    // Convert string to Long for numeric lookup
                    Long numericBarcode = Long.parseLong(codeBarre);
                    System.out.println("Converting barcode to Long: " + numericBarcode);
                    
                    // Use the dedicated numeric lookup method
                    result = produitRepository.findByCodeBarreNumeric(numericBarcode);
                    System.out.println("Result after numeric conversion: " + (result.isPresent() ? "Found" : "Not found"));
                    
                    // If still not found, try again with a different query approach
                    if (result.isEmpty()) {
                        System.out.println("Trying alternative query approaches...");
                        // Try with toString to ensure format matching
                        String numericString = numericBarcode.toString();
                        result = produitRepository.findByCodeBarre(numericString);
                        System.out.println("Result after string conversion: " + (result.isPresent() ? "Found" : "Not found"));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Failed to parse barcode as Long: " + e.getMessage());
                }
            } else {
                System.out.println("Found product with string barcode directly");
            }
            
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
