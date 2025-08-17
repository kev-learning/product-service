package com.microservices.core.product.service.service;

import com.microservices.core.product.service.dto.ProductDTO;
import com.microservices.core.product.service.mapper.ProductMapper;
import com.microservices.core.product.service.model.Product;
import com.microservices.core.product.service.repository.ProductRepository;
import com.microservices.core.util.exceptions.InvalidInputException;
import com.microservices.core.util.exceptions.NotFoundException;
import com.microservices.core.util.http.ServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private static final Long COMMON_ID = 1L;

    @Mock
    private ServiceUtil serviceUtil;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Spy
    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProductTest() {
        Mockito.when(productMapper.DTOtoEntity(Mockito.any(ProductDTO.class))).thenReturn(buildProduct());
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(buildProduct());
        Mockito.when(productMapper.entityToDTO(Mockito.any(Product.class))).thenReturn(buildProductDTO());

        ProductDTO productDTO = productService.createProduct(buildProductDTO());

        assertNotNull(productDTO);
    }

    @Test
    void getProductTest() {
        Mockito.when(productRepository.findByProductId(Mockito.anyLong())).thenReturn(Optional.of(buildProduct()));
        Mockito.when(productMapper.entityToDTO(Mockito.any(Product.class))).thenReturn(buildProductDTO());

        ProductDTO productDTO = productService.getProduct(COMMON_ID);

        assertNotNull(productDTO);
    }

    @Test
    void getProductInvalidIdTest() {
        assertThrows(InvalidInputException.class, () -> productService.getProduct(0L));
        assertThrows(InvalidInputException.class, () -> productService.getProduct(null));
    }

    @Test
    void getProductNotFoundTest() {
        Mockito.when(productRepository.findByProductId(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getProduct(COMMON_ID));
    }

    @Test
    void deleteProductInvalidIdTest() {
        assertThrows(InvalidInputException.class, () -> productService.deleteProduct(0L));
        assertThrows(InvalidInputException.class, () -> productService.deleteProduct(null));
    }

    @Test
    void deleteProductTest() {
        Mockito.when(productRepository.findByProductId(Mockito.anyLong())).thenReturn(Optional.of(buildProduct()));
        Mockito.doNothing().when(productRepository).delete(Mockito.any(Product.class));

        productService.deleteProduct(COMMON_ID);
    }

    @Test
    void deleteProductNotFoundTest() {
        Mockito.when(productRepository.findByProductId(Mockito.anyLong())).thenReturn(Optional.empty());

        productService.deleteProduct(COMMON_ID);

        Mockito.verify(productRepository, Mockito.never()).delete(Mockito.any(Product.class));
    }

    private Product buildProduct() {
        Product product = new Product();
        product.setProductId(COMMON_ID);
        product.setId("ID");
        product.setVersion(COMMON_ID.intValue());
        product.setName("NAME");
        product.setWeight(COMMON_ID.intValue());

        return product;
    }

    private ProductDTO buildProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(COMMON_ID);
        productDTO.setName("NAME");
        productDTO.setWeight(COMMON_ID.intValue());

        return productDTO;
    }
}