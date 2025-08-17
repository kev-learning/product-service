package com.microservices.core.product.service.repository;

import com.microservices.core.product.service.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, String>, CrudRepository<Product, String> {

    Optional<Product> findByProductId(Long productId);
}
