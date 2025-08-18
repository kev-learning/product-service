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

import java.util.Objects;

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
    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            Product product = productMapper.DTOtoEntity(productDTO);
            product = productRepository.save(product);

            log.debug("Created new product: {}", product);
            return productMapper.entityToDTO(product, serviceUtil.getAddress());
        }catch(DuplicateKeyException dke) {
            throw new InvalidInputException("Duplicate key for product ID: " + productDTO.getProductId());
        }
    }

    @Override
    public ProductDTO getProduct(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid product ID: " + productId);
        }

        Product product = productRepository.findByProductId(productId).orElseThrow(() -> new NotFoundException("Product not found for product ID: " + productId));
        ProductDTO productDTO = productMapper.entityToDTO(product, serviceUtil.getAddress());
        productDTO.setServiceAddress(serviceUtil.getAddress());

        log.debug("Found product: {}", productDTO);

        return productDTO;
    }

    @Override
    public void deleteProduct(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid product ID: " + productId);
        }

        log.debug("Delete product with product ID: {}", productId);
        productRepository.findByProductId(productId).ifPresent(productRepository::delete);
    }
}
