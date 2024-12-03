package com.certidevs.controller;

import com.certidevs.entity.Product;
import com.certidevs.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // https://alan.com/hola
    // http://localhost:8080/hola
    @GetMapping("hola")
    public String holaMundo() {
        return "Hola mundo desde Spring Boot";
    }

    @GetMapping("api/products")
    public List<Product> findAll() {
//        List<Product> products = List.of(
//                new Product(1L, "ordenador", 500d),
//                new Product(2L, "smartphone", 450d),
//                new Product(3L, "botella", 450d)
//        );
        // return products;
        return productRepository.findAll();
    }

    @PostMapping("api/products")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
