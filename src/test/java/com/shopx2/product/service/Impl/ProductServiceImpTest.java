package com.shopx2.product.service.Impl;

import com.shopx2.product.dto.ProductDTO;
import com.shopx2.product.entity.Category;
import com.shopx2.product.entity.Product;
import com.shopx2.product.exception.ResourceNotFoundException;
import com.shopx2.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.shopx2.product.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ProductServiceImp productServiceImp;

    @Test
    void addProductsTest() {
        // Setup
        List<ProductDTO> productsDTO = List.of(IPHONE_PRODUCT_DTO, SHOES_PRODUCT_DTO);
        when(productRepository.save(IPHONE_PRODUCT)).thenReturn(IPHONE_PRODUCT);
        when(productRepository.save(SHOES_PRODUCT)).thenReturn(SHOES_PRODUCT);
        when(mapper.map(IPHONE_PRODUCT_DTO, Product.class)).thenReturn(IPHONE_PRODUCT);
        when(mapper.map(SHOES_PRODUCT_DTO, Product.class)).thenReturn(SHOES_PRODUCT);
        when(mapper.map(IPHONE_PRODUCT, ProductDTO.class)).thenReturn(IPHONE_PRODUCT_DTO);
        when(mapper.map(SHOES_PRODUCT, ProductDTO.class)).thenReturn(SHOES_PRODUCT_DTO);

        // Execute
        List<ProductDTO> addedProducts = productServiceImp.addProducts(productsDTO);

        // Assert
        assertEquals(productsDTO, addedProducts);
    }

    @Test
    void addSingleProductTest() {
        // Setup
        when(productRepository.save(IPHONE_PRODUCT)).thenReturn(IPHONE_PRODUCT);
        when(mapper.map(IPHONE_PRODUCT_DTO, Product.class)).thenReturn(IPHONE_PRODUCT);
        when(mapper.map(IPHONE_PRODUCT, ProductDTO.class)).thenReturn(IPHONE_PRODUCT_DTO);

        // Execute
        ProductDTO addedProduct = productServiceImp.addProduct(IPHONE_PRODUCT_DTO);

        // Assert
        assertEquals(IPHONE_PRODUCT_DTO, addedProduct);
    }

    @Test
    void getProductByIDTest() {
        // Setup
        when(productRepository.findById(IPHONE_PRODUCT.getId())).thenReturn(Optional.of(IPHONE_PRODUCT));
        when(mapper.map(IPHONE_PRODUCT, ProductDTO.class)).thenReturn(IPHONE_PRODUCT_DTO);

        // Execute
        ProductDTO productByID = productServiceImp.getProductByID(IPHONE_PRODUCT.getId().toString());

        // Assert
        assertEquals(IPHONE_PRODUCT_DTO, productByID);
    }

    @Test
    void getProductsTest() {
        // Setup
        when(productRepository.findAll()).thenReturn(List.of(IPHONE_PRODUCT, SAMSUNG_PRODUCT, SHOES_PRODUCT));
        when(mapper.map(IPHONE_PRODUCT, ProductDTO.class)).thenReturn(IPHONE_PRODUCT_DTO);
        when(mapper.map(SAMSUNG_PRODUCT, ProductDTO.class)).thenReturn(SAMSUNG_PRODUCT_DTO);

        Category applianceCategory = Category.APPLIANCES;
        BigDecimal minValue = new BigDecimal(15);
        BigDecimal maxValue = new BigDecimal(1000);

        // Execute
        List<ProductDTO> appliances = productServiceImp.getProducts(List.of(applianceCategory), minValue, maxValue);
        List<ProductDTO> cheapAppliances = productServiceImp.getProducts(List.of(applianceCategory), minValue, new BigDecimal(500));

        // Assert
        assertEquals(List.of(IPHONE_PRODUCT_DTO, SAMSUNG_PRODUCT_DTO), appliances);
        assertEquals(List.of(SAMSUNG_PRODUCT_DTO), cheapAppliances);
    }

    @Test
    void deleteProductByIdTest() {
        // Setup
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        when(productRepository.findById(IPHONE_PRODUCT.getId())).thenReturn(Optional.of(IPHONE_PRODUCT));
        when(mapper.map(IPHONE_PRODUCT, ProductDTO.class)).thenReturn(IPHONE_PRODUCT_DTO);

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> productServiceImp.deleteProductById(id.toString()));
        assertEquals(IPHONE_PRODUCT_DTO, productServiceImp.deleteProductById(IPHONE_PRODUCT.getId().toString()));
    }
}