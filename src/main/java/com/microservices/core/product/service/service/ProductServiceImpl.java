package com.microservices.core.product.service.service;

import com.microservices.core.product.service.dto.ProductDTO;
import com.microservices.core.product.service.mapper.ProductMapper;
import com.microservices.core.product.service.model.Product;
import com.microservices.core.product.service.repository.ProductRepository;
import com.microservices.core.util.exceptions.InvalidInputException;
import com.microservices.core.util.exceptions.NotFoundException;
import com.microservices.core.util.http.ServiceUtil;
import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.logging.Level;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Mono<ProductDTO> createProduct(ProductDTO productDTO) {
        try {
            Product product = productMapper.DTOtoEntity(productDTO);
            Mono<ProductDTO> createdProduct = productRepository.save(product)
                    .log(log.getName(), Level.FINE)
                    .onErrorMap(DuplicateKeyException.class, ex -> new InvalidInputException("Duplicate key for Product ID: " + productDTO.getProductId()))
                    .map(entity -> productMapper.entityToDTO(entity, serviceUtil.getAddress()));

            log.debug("Created new product: {}", createdProduct);
            return createdProduct;
        }catch(DuplicateKeyException dke) {
            throw new InvalidInputException("Duplicate key for product ID: " + productDTO.getProductId());
        }
    }

    @Override
    public Mono<ProductDTO> getProduct(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid product ID: " + productId);
        }

        Mono<ProductDTO> product = productRepository.findByProductId(productId)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found for product ID: " + productId)))
                .log(log.getName(), Level.FINE)
                .map(entity -> productMapper.entityToDTO(entity, serviceUtil.getAddress()));

        log.debug("Found product: {}", product);

        return product;
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid product ID: " + productId);
        }

        log.debug("Delete product with product ID: {}", productId);
        return productRepository.findByProductId(productId).log(log.getName(), Level.FINE).map(productRepository::delete).flatMap(e -> e);
    }
}
