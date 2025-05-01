package com.shopx2.product.service.Impl;

import com.shopx2.product.dto.CreateProductDTO;
import com.shopx2.product.dto.GetProductDTO;
import com.shopx2.product.entity.Category;
import com.shopx2.product.entity.Product;
import com.shopx2.product.exception.ResourceNotFoundException;
import com.shopx2.product.repository.ProductRepository;
import com.shopx2.product.service.ProductService;
import com.shopx2.product.util.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private ProductRepository repository;

    @Override
    public GetProductDTO addProduct(CreateProductDTO productDTO) {
        Product product = mapper.mapToProduct(productDTO);
        Product save = repository.save(product);
        return mapper.mapToGetProductDTO(save);
    }

    @Override
    public GetProductDTO getProductByID(String productUUID) {
        Product product = repository
            .findById(UUID.fromString(productUUID))
            .orElseThrow();
        return mapper.mapToGetProductDTO(product);
    }

    @Override
    public List<GetProductDTO> getProducts(List<Category> category,
                                           BigDecimal minValue,
                                           BigDecimal maxValue) {
        return repository.findAll()
            .stream()
            .filter(product -> filterByCategory(product, category))
            .filter(product -> filterByPrice(product, minValue, maxValue))
            .map(product -> mapper.mapToGetProductDTO(product))
            .toList();
    }

    private boolean filterByCategory(Product product, List<Category> categories) {
        if (categories == null || categories.isEmpty()) return true;
        return categories.contains(product.getCategory());
    }

    private boolean filterByPrice(Product product, BigDecimal minValue, BigDecimal maxValue) {
        boolean withRangePrice = true;
        if (minValue != null) {
            withRangePrice = product.getPrice().compareTo(minValue) >= 0;
        }
        if (maxValue != null) {
            withRangePrice = product.getPrice().compareTo(maxValue) <= 0;
        }
        return withRangePrice;
    }

    @Override
    public GetProductDTO deleteProductById(String id) {
        Product product;
        try {
            product = repository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new);
            repository.delete(product);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(UUID.fromString(id));
        }
        return mapper.mapToGetProductDTO(product);
    }
}
