package com.shopx2.product.util;

import com.shopx2.product.dto.CreateProductDTO;
import com.shopx2.product.dto.GetProductDTO;
import com.shopx2.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    GetProductDTO mapToGetProductDTO(Product product);

    Product mapToProduct(CreateProductDTO productDTO);
}
