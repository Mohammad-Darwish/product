package com.shopx2.product.service.Impl;

import com.shopx2.product.dto.GetProductDTO;
import com.shopx2.product.entity.Category;
import com.shopx2.product.exception.ResourceNotFoundException;
import com.shopx2.product.repository.ProductRepository;
import com.shopx2.product.util.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImp productServiceImp;

    @Test
    void addProductTest() {
        // Setup
        when(productRepository.save(IPHONE_PRODUCT)).thenReturn(IPHONE_PRODUCT);
        when(mapper.mapToProduct(CREATE_IPHONE_PRODUCT_DTO)).thenReturn(IPHONE_PRODUCT);
        when(mapper.mapToGetProductDTO(IPHONE_PRODUCT)).thenReturn(GET_IPHONE_PRODUCT_DTO);

        // Execute
        GetProductDTO addedProduct = productServiceImp.addProduct(CREATE_IPHONE_PRODUCT_DTO);

        // Assert
        assertEquals(GET_IPHONE_PRODUCT_DTO, addedProduct);
    }

    @Test
    void getProductByIDTest() {
        // Setup
        when(productRepository.findById(IPHONE_PRODUCT.getId())).thenReturn(Optional.of(IPHONE_PRODUCT));
        when(mapper.mapToGetProductDTO(IPHONE_PRODUCT)).thenReturn(GET_IPHONE_PRODUCT_DTO);

        // Execute
        GetProductDTO productByID = productServiceImp.getProductByID(IPHONE_PRODUCT.getId().toString());

        // Assert
        assertEquals(GET_IPHONE_PRODUCT_DTO, productByID);
    }

    @Test
    void getProductsTest() {
        // Setup
        when(productRepository.findAll()).thenReturn(List.of(IPHONE_PRODUCT, SAMSUNG_PRODUCT, SHOES_PRODUCT));
        when(mapper.mapToGetProductDTO(IPHONE_PRODUCT)).thenReturn(GET_IPHONE_PRODUCT_DTO);
        when(mapper.mapToGetProductDTO(SAMSUNG_PRODUCT)).thenReturn(GET_SAMSUNG_PRODUCT_DTO);

        Category applianceCategory = Category.APPLIANCES;
        BigDecimal minValue = new BigDecimal(15);
        BigDecimal maxValue = new BigDecimal(1000);

        // Execute
        List<GetProductDTO> appliances = productServiceImp.getProducts(List.of(applianceCategory), minValue, maxValue);
        List<GetProductDTO> cheapAppliances = productServiceImp.getProducts(List.of(applianceCategory), minValue, new BigDecimal(500));

        // Assert
        assertEquals(List.of(GET_IPHONE_PRODUCT_DTO, GET_SAMSUNG_PRODUCT_DTO), appliances);
        assertEquals(List.of(GET_SAMSUNG_PRODUCT_DTO), cheapAppliances);
    }

    @Test
    void deleteProductByIdTest() {
        // Setup
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        when(productRepository.findById(IPHONE_PRODUCT.getId())).thenReturn(Optional.of(IPHONE_PRODUCT));
        when(mapper.mapToGetProductDTO(IPHONE_PRODUCT)).thenReturn(GET_IPHONE_PRODUCT_DTO);

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> productServiceImp.deleteProductById(id.toString()));
        assertEquals(GET_IPHONE_PRODUCT_DTO, productServiceImp.deleteProductById(IPHONE_PRODUCT.getId().toString()));
    }
}