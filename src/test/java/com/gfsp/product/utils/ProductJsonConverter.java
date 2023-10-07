package com.gfsp.product.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfsp.product.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductJsonConverter {
    public static String toStringJson(ObjectMapper mapper, Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ProductDTO> jsonToListProduct(ObjectMapper mapper, String json) {
        List<ProductDTO> list = new ArrayList<>();
        try {
            list = mapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
        return list;
    }

    public static ProductDTO jsonToProduct(ObjectMapper mapper, String json) {
        ProductDTO productDTO = new ProductDTO();
        try {
            productDTO = mapper.readValue(json, ProductDTO.class);
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
        return productDTO;
    }
}
