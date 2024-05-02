package com.userproduct.controller;

import com.userproduct.exception.NoProductsFoundException;
import com.userproduct.exception.ProductAlreadyExistsException;
import com.userproduct.model.Product;
import com.userproduct.model.User;
import com.userproduct.repository.ProductRepo;
import com.userproduct.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) {
            throw new NoProductsFoundException("No Products found");
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        Optional<Product> productOptional = productRepo.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + productId);
        }
        return ResponseEntity.ok(productOptional.get());
    }

    @GetMapping("/getProduct/{userId}")
    public ResponseEntity<?> getProductByUserId(@PathVariable Long userId) {
    Optional<User> userOptional = userRepo.findById(userId);
    if (userOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
    }
        Optional<Product> productOptional = productRepo.findById(userId);
        return ResponseEntity.ok(productOptional.get());

}

    @PostMapping("/post")
    public ResponseEntity<String> postData(@RequestBody Product product) {
        if (productRepo.existsById(product.getProductId())) {
            throw new ProductAlreadyExistsException("Product with product ID already exists");
        }
        productRepo.save(product);
        return ResponseEntity.ok("Success");
    }



    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        if (!productRepo.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }
        // Ensure the ID of updatedProduct matches the path variable
        updatedProduct.setId(productId);

        // Save the updated product
        productRepo.save(updatedProduct);

        return ResponseEntity.ok("Product updated successfully");
    }


    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        if (!productRepo.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }
        productRepo.deleteById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }





}
