package com.microservices.core.product.service.controller;

import com.microservices.core.product.service.service.ProductService;
import com.microservices.core.product.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ProductDTO>> getProductById(@PathVariable(name = "productId") Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PostMapping(value = "/product/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/product/{productId}")
    public Mono<Void> deleteProduct(@PathVariable(name = "productId") Long productId) {
        return productService.deleteProduct(productId);
    }
}
