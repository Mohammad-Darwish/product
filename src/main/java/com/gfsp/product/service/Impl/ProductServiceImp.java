package com.gfsp.product.service.Impl;

import com.gfsp.product.dto.ProductDTO;
import com.gfsp.product.entity.Category;
import com.gfsp.product.entity.Product;
import com.gfsp.product.exception.ResourceNotFoundException;
import com.gfsp.product.repository.ProductRepository;
import com.gfsp.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository repository;

    @Override
    public List<ProductDTO> addProducts(List<ProductDTO> productsDto) {
        List<Product> savedProducts = productsDto.stream()
            .map(productDTO -> mapper.map(productDTO, Product.class))
            .map(this::saveProduct)
            .toList();
        return savedProducts.stream().map(product -> mapper.map(product, ProductDTO.class)).toList();
    }

    private Product saveProduct(Product product) {
        Optional<Product> any = repository.findById(product.getId()).stream().findAny();
        any.ifPresentOrElse(
            product1 -> {
                product.setQuantity(product.getQuantity() + product1.getQuantity());
                repository.save(product);
            },
            () -> repository.save(product)
        );
        return product;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        return Stream.ofNullable(productDTO)
            .map(product -> mapper.map(product, Product.class))
            .map(this::saveProduct)
            .map(savedProduct -> mapper.map(savedProduct, ProductDTO.class))
            .findAny()
            .orElseThrow();
    }

    @Override
    public ProductDTO getProductByID(String productUUID) {
        Product product = repository
            .findById(UUID.fromString(productUUID))
            .orElseThrow();
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getProducts(List<Category> category,
                                        BigDecimal minValue,
                                        BigDecimal maxValue) {
        return repository.findAll()
            .stream()
            .filter(product -> filterByCategory(product, category))
            .filter(product -> filterByPrice(product, minValue, maxValue))
            .map(product -> mapper.map(product, ProductDTO.class))
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
    public ProductDTO deleteProductById(String id) {
        Product product;
        try {
            product = repository.findById(UUID.fromString(id)).orElseThrow(IllegalArgumentException::new);
            repository.delete(product);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(UUID.fromString(id));
        }
        return mapper.map(product, ProductDTO.class);
    }
}
