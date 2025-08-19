package com.microservices.core.product.service.repository;

import com.microservices.core.product.service.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    Mono<Product> findByProductId(Long productId);
}
