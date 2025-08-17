package com.microservices.core.product.service.service;

import com.microservices.core.product.service.dto.ProductDTO;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO getProduct(Long productId);

    void deleteProduct(Long productId);
}
