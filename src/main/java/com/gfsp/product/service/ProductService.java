package com.gfsp.product.service;

import com.gfsp.product.dto.ProductDTO;
import com.gfsp.product.entity.Category;

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
