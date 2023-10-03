package com.gfsp.product.service.Impl;

import com.gfsp.product.dto.ProductDTO;
import com.gfsp.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImp implements ProductService {
    @Override
    public List<ProductDTO> addProducts(List<ProductDTO> products) {
        return null;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO getProductByID(String productUUID) {
        return null;
    }

    @Override
    public List<ProductDTO> getProductsByName(String productName) {
        return null;
    }
}
