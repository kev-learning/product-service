package com.microservices.core.product.service.service;

import com.microservices.core.product.service.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDTO> createProduct(ProductDTO productDTO);

    Mono<ProductDTO> getProduct(Long productId);

    Mono<Void> deleteProduct(Long productId);
}
