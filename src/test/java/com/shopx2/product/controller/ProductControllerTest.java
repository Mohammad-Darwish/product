package com.shopx2.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopx2.product.dto.ProductDTO;
import com.shopx2.product.entity.Category;
import com.shopx2.product.service.Impl.ProductServiceImp;
import com.shopx2.product.utils.ProductJsonConverter;
import com.shopx2.product.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class) // unit test, Doesn't load all spring application context
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductServiceImp productService;

    @Test
    void addProductsCreatedTest() throws Exception {
        Mockito.when(productService.addProducts(List.of(TestUtils.IPHONE_PRODUCT_DTO))).thenReturn(List.of(TestUtils.IPHONE_PRODUCT_DTO));
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/products")
                .content(ProductJsonConverter.toStringJson(mapper, List.of(TestUtils.IPHONE_PRODUCT_DTO)))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        List<ProductDTO> result = ProductJsonConverter.jsonToListProduct(mapper, response.getContentAsString());
        Assertions.assertEquals(TestUtils.IPHONE_PRODUCT_DTO, result.get(0));
    }

    @Test
    void addProductsInvalidTest() throws Exception {
        assertThrows(jakarta.servlet.ServletException.class, () -> mockMvc.perform(
            post("/api/v1/products")
                .content(ProductJsonConverter.toStringJson(mapper, List.of(TestUtils.INVALID_PRODUCT_DTO)))
                .contentType(MediaType.APPLICATION_JSON)));
    }

    @Test
    void getProductByIdTest() throws Exception {
        Mockito.when(productService.getProductByID(TestUtils.IPHONE_PRODUCT_DTO.getId().toString())).thenReturn(TestUtils.IPHONE_PRODUCT_DTO);
        MockHttpServletResponse response = mockMvc.perform(get(String.format("/api/v1/products/%s", TestUtils.IPHONE_PRODUCT_DTO.getId())))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        ProductDTO result = ProductJsonConverter.jsonToProduct(mapper, response.getContentAsString());
        Assertions.assertEquals(TestUtils.IPHONE_PRODUCT_DTO, result);
    }

    @Test
    void getProductByNameValidTest() throws Exception {
        // SETUP
        Mockito.when(productService.getProducts(List.of(Category.APPLIANCES), null, null)).thenReturn(List.of(TestUtils.IPHONE_PRODUCT_DTO));
        String url = String.format("/api/v1/products?category=%s", TestUtils.IPHONE_PRODUCT_DTO.getCategory());

        //EXECUTE
        MockHttpServletResponse response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        List<ProductDTO> result = ProductJsonConverter.jsonToListProduct(mapper, response.getContentAsString());

        // ASSERT
        Assertions.assertEquals(TestUtils.IPHONE_PRODUCT_DTO, result.get(0));
    }

    @Test
    void deleteProductByIdTest() throws Exception {
        Mockito.when(productService.deleteProductById(TestUtils.IPHONE_PRODUCT_DTO.getId().toString())).thenReturn(TestUtils.IPHONE_PRODUCT_DTO);
        MockHttpServletRequestBuilder requestBuilder =
            delete(
                String.format("/api/v1/products/%s", TestUtils.IPHONE_PRODUCT_DTO.getId()))
                .contentType(MediaType.APPLICATION_JSON
                );

        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk());
    }
}
