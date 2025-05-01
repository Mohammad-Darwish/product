package com.shopx2.product.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopx2.product.dto.GetProductDTO;

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

    public static List<GetProductDTO> jsonToListProduct(ObjectMapper mapper, String json) {
        List<GetProductDTO> list = new ArrayList<>();
        try {
            list = mapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
        return list;
    }

    public static GetProductDTO jsonToProduct(ObjectMapper mapper, String json) {
        GetProductDTO productDTO = null;
        try {
            productDTO = mapper.readValue(json, GetProductDTO.class);
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
        return productDTO;
    }
}
