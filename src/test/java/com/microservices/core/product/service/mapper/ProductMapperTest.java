package com.microservices.core.product.service.mapper;

import com.microservices.core.product.service.dto.ProductDTO;
import com.microservices.core.product.service.model.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private static final Long COMMON_ID = 1L;

    @Test
    void mapEntityToDTOTest() {
        Product product = new Product();
        product.setProductId(COMMON_ID);
        product.setId("ID");
        product.setVersion(COMMON_ID.intValue());
        product.setName("NAME");
        product.setWeight(COMMON_ID.intValue());

        ProductDTO productDTO = productMapper.entityToDTO(product, "Address");

        assertEquals(product.getProductId(), productDTO.getProductId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getWeight(), productDTO.getWeight());
        assertEquals("Address", productDTO.getServiceAddress());
    }

    @Test
    void mapDTOToEntityTest() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(COMMON_ID);
        productDTO.setName("NAME");
        productDTO.setWeight(COMMON_ID.intValue());

        Product product = productMapper.DTOtoEntity(productDTO);

        assertEquals(productDTO.getProductId(), product.getProductId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getWeight(), product.getWeight());
    }

}