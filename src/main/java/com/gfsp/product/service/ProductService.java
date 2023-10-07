package com.gfsp.product.service;

import com.gfsp.product.dto.ProductDTO;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface ProductService {
    List<ProductDTO> addProducts(List<ProductDTO> productDTOS);

    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO getProductByID(String productUUID);

    List<ProductDTO> getProducts(MultiValueMap<String, String> productName);

    boolean deleteProductById(String id);
}
