package com.shopx2.product.service;

import com.shopx2.product.dto.CreateProductDTO;
import com.shopx2.product.dto.GetProductDTO;
import com.shopx2.product.entity.Category;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    GetProductDTO addProduct(CreateProductDTO productDTO);

    GetProductDTO getProductByID(String productUUID);

    List<GetProductDTO> getProducts(List<Category> category,
                                    BigDecimal minValue,
                                    BigDecimal maxValue);

    GetProductDTO deleteProductById(String id);
}
