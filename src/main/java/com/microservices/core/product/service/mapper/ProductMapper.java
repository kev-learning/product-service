package com.microservices.core.product.service.mapper;

import com.microservices.core.product.service.dto.ProductDTO;
import com.microservices.core.product.service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", source = "address")
    })
    ProductDTO entityToDTO(Product product, String address);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    Product DTOtoEntity(ProductDTO productDTO);
}
