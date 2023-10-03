package com.gfsp.product.service;

import com.gfsp.product.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDTO> addProducts(List<ProductDTO> productDTOS);
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO getProductByID(String productUUID);
    List<ProductDTO> getProductsByName(String productName);
}
