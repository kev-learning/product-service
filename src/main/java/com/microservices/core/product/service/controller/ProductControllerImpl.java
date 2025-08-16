package com.microservices.core.product.service.controller;

import com.microservices.core.util.http.ServiceUtil;
import com.microservices.core.product.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductControllerImpl implements ProductController{

    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public ResponseEntity<Object> getProductById(Long productId) {
        ProductDTO productDTO = ProductDTO.builder()
                .productId(productId)
                .name("Product-" + productId)
                .weight(10)
                .serviceAddress(serviceUtil.getAddress())
                .build();
        return ResponseEntity.ok(productDTO);
    }
}
