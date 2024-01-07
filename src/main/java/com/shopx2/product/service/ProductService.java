package com.shopx2.product.service;

import com.shopx2.product.dto.ProductDTO;
import com.shopx2.product.entity.Category;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductDTO> addProducts(List<ProductDTO> productDTOS);

    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO getProductByID(String productUUID);

    List<ProductDTO> getProducts(List<Category> category,
                                 BigDecimal minValue,
                                 BigDecimal maxValue);

    ProductDTO deleteProductById(String id);
}
