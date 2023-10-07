package com.gfsp.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfsp.product.dto.ProductDTO;
import com.gfsp.product.service.Impl.ProductServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.gfsp.product.utils.ProductJsonConverter.*;
import static com.gfsp.product.utils.TestUtils.INVALID_PRODUCT_DTO;
import static com.gfsp.product.utils.TestUtils.IPHONE_PRODUCT_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Mockito.when(productService.addProducts(List.of(IPHONE_PRODUCT_DTO))).thenReturn(List.of(IPHONE_PRODUCT_DTO));
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/products")
                .content(toStringJson(mapper, List.of(IPHONE_PRODUCT_DTO)))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        List<ProductDTO> result = jsonToListProduct(mapper, response.getContentAsString());
        assertEquals(IPHONE_PRODUCT_DTO, result.get(0));
    }

    @Test
    void addProductsInvalidTest() throws Exception {
        assertThrows(jakarta.servlet.ServletException.class, () -> mockMvc.perform(
            post("/api/v1/products")
                .content(toStringJson(mapper, List.of(INVALID_PRODUCT_DTO)))
                .contentType(MediaType.APPLICATION_JSON)));
    }

    @Test
    void getProductByIdTest() throws Exception {
        Mockito.when(productService.getProductByID(IPHONE_PRODUCT_DTO.getId().toString())).thenReturn(IPHONE_PRODUCT_DTO);
        MockHttpServletResponse response = mockMvc.perform(get(String.format("/api/v1/products/%s", IPHONE_PRODUCT_DTO.getId())))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        ProductDTO result = jsonToProduct(mapper, response.getContentAsString());
        assertEquals(IPHONE_PRODUCT_DTO, result);
    }

    @Test
    void getProductByNameValidTest() throws Exception {
        // SETUP
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("productName", List.of(IPHONE_PRODUCT_DTO.getProductName()));
        Mockito.when(productService.getProducts(multiValueMap)).thenReturn(List.of(IPHONE_PRODUCT_DTO));
        String url = String.format("/api/v1/products?productName=%s", IPHONE_PRODUCT_DTO.getProductName());

        //EXECUTE
        MockHttpServletResponse response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        List<ProductDTO> result = jsonToListProduct(mapper, response.getContentAsString());

        // ASSERT
        assertEquals(IPHONE_PRODUCT_DTO, result.get(0));
    }

    @Test
    void deleteProductByIdTest() throws Exception {
        Mockito.when(productService.deleteProductById(IPHONE_PRODUCT_DTO.getId().toString())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder =
            delete(
                String.format("/api/v1/products/%s", IPHONE_PRODUCT_DTO.getId()))
                .contentType(MediaType.APPLICATION_JSON
                );

        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk());
    }
}
