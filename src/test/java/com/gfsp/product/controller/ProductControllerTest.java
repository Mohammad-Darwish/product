package com.gfsp.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.ArrayList;
import java.util.List;

import static com.gfsp.product.utils.TestUtils.INVALID_PRODUCT_DTO;
import static com.gfsp.product.utils.TestUtils.IPHONE_PRODUCT_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private String asString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ProductDTO> jsonToListProduct(String json) {
        List<ProductDTO> list = new ArrayList<>();
        try {
            list = mapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
        return list;
    }
    private ProductDTO jsonToProduct(String json) {
        ProductDTO productDTO = new ProductDTO();
        try {
            productDTO = mapper.readValue(json, ProductDTO.class);
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
        return productDTO;
    }

    @Test
    void addProductsCreatedTest() throws Exception {
        Mockito.when(productService.addProducts(List.of(IPHONE_PRODUCT_DTO))).thenReturn(List.of(IPHONE_PRODUCT_DTO));
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/products")
                .content(asString(List.of(IPHONE_PRODUCT_DTO)))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        List<ProductDTO> result = jsonToListProduct(response.getContentAsString());
        assertEquals(IPHONE_PRODUCT_DTO, result.get(0));
    }

    @Test
    void addProductsInvalidTest() throws Exception {
        assertThrows(jakarta.servlet.ServletException.class, () -> mockMvc.perform(
            post("/api/v1/products")
                .content(asString(List.of(INVALID_PRODUCT_DTO)))
                .contentType(MediaType.APPLICATION_JSON)));
    }

    @Test
    void getProductByIDValidTest() throws Exception {
        Mockito.when(productService.getProductByID(IPHONE_PRODUCT_DTO.getId().toString())).thenReturn(IPHONE_PRODUCT_DTO);
        MockHttpServletResponse response = mockMvc.perform(get(String.format("/api/v1/products/%s", IPHONE_PRODUCT_DTO.getId())))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        ProductDTO result = jsonToProduct(response.getContentAsString());
        assertEquals(IPHONE_PRODUCT_DTO, result);
    }


    @Test
    void getProductByNameValidTest() throws Exception {
        Mockito.when(productService.getProductsByName(IPHONE_PRODUCT_DTO.getProductName())).thenReturn(List.of(IPHONE_PRODUCT_DTO));
        MockHttpServletResponse response = mockMvc.perform(get(String.format("/api/v1/products/%s", IPHONE_PRODUCT_DTO.getProductName())))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        List<ProductDTO> result = jsonToListProduct(response.getContentAsString());
        assertEquals(IPHONE_PRODUCT_DTO, result.get(0));
    }
}
