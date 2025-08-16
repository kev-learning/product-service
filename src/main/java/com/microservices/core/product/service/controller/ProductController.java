package com.microservices.core.product.service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductController {

    @GetMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getProductById(@PathVariable(name = "productId") Long productId);
}
