package com.hbn.apitesting.controller;

import com.hbn.apitesting.entity.Product;
import com.hbn.apitesting.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ Get All Products
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "No products available"));
        }
        return ResponseEntity.ok(products);
    }

    // ✅ Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            return product.isPresent()
                    ? ResponseEntity.ok(product.get())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));
        }
    }

    // ✅ Create Product with Validation
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", result.getAllErrors().get(0).getDefaultMessage()));
        }

        try {
            Product savedProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (ResponseStatusException ex) { // Catch validation failures
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of("error", ex.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to create product"));
        }
    }


    // ✅ Update Product
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", result.getAllErrors().get(0).getDefaultMessage()));
        }

        try {
            Optional<Product> updatedProduct = productService.updateProduct(id, productDetails);
            return updatedProduct.isPresent()
                    ? ResponseEntity.ok(updatedProduct.get())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found"));
        } catch (ResponseStatusException ex) { // Catch validation failures
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of("error", ex.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update product"));
        }
    }


    // ✅ Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            boolean isDeleted = productService.deleteProduct(id);
            return isDeleted
                    ? ResponseEntity.ok(Map.of("message", "Product deleted successfully"))
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to delete product"));
        }
    }
}
