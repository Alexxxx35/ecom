package com.quest.etna.controller;


import com.quest.etna.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.quest.etna.repositories.ProductRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/products/")
    public ResponseEntity<Object> getAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/product/{id}", produces = "application/json")
    public ResponseEntity<Object> getProductById(@PathVariable Integer id) {
        if (productRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("{\"Error\": \"NOT FOUND\"}", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productRepository.findById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/product/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateProduct(@PathVariable int id, @RequestBody Product product) {

        if (product.getNomProduit() != null)
            productRepository.setNomProduitById(id, product.getNomProduit());
        if (product.getPrix() > 0 )
            productRepository.setPrixById(id, product.getPrix());

        return new ResponseEntity<>(productRepository.findById(id), HttpStatus.OK);

    }
    
    @DeleteMapping(value = "/product/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);
    
        if (productRepository.existsById(id)) {
            return new ResponseEntity<>("{\"success\": false }", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("{\"success\": true }", HttpStatus.OK);
        }
    }

}
