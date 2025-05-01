package com.shopx2.product.utils;

import com.shopx2.product.dto.CreateProductDTO;
import com.shopx2.product.dto.GetProductDTO;
import com.shopx2.product.entity.Category;
import com.shopx2.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TestUtils {
    public static Product IPHONE_PRODUCT =
        new Product(
            UUID.randomUUID(),
            "IPHONE 14",
            "IPHONE",
            LocalDate.parse("2022-05-15"),
            null,
            Category.APPLIANCES,
            "This is a phone device",
            new BigDecimal(900)
        );

    public static Product SHOES_PRODUCT =
        new Product(
            UUID.randomUUID(),
            "shoes",
            "Adidas",
            LocalDate.parse("2020-05-15"),
            null,
            Category.CLOTHES,
            "This shoes is a sporty shoes",
            new BigDecimal(50)
        );

    public static Product SAMSUNG_PRODUCT =
        new Product(
            UUID.fromString("00d41db1-11a9-4168-bd1a-9cab7708b760"),
            "Watch 5",
            "SAMSUNG",
            LocalDate.parse("2022-10-15"),
            null,
            Category.APPLIANCES,
            "This is a smart watch device",
            new BigDecimal(200)
        );

    public static CreateProductDTO CREATE_IPHONE_PRODUCT_DTO =
        new CreateProductDTO(
            "IPHONE 14",
            "IPHONE",
            LocalDate.parse("2022-05-15"),
            null,
            Category.APPLIANCES,
            "This is a phone device",
            new BigDecimal(900)
        );

    public static GetProductDTO GET_IPHONE_PRODUCT_DTO =
        new GetProductDTO(
            UUID.randomUUID(),
            "IPHONE 14",
            "IPHONE",
            LocalDate.parse("2022-05-15"),
            null,
            Category.APPLIANCES,
            "This is a phone device",
            new BigDecimal(900)
        );

    public static GetProductDTO GET_INVALID_PRODUCT_DTO =
        new GetProductDTO(
            UUID.randomUUID(),
            null,
            null,
            LocalDate.parse("2022-05-15"),
            null,
            Category.APPLIANCES,
            "This is a phone device",
            new BigDecimal(900)
        );

    public static GetProductDTO GET_SHOES_PRODUCT_DTO =
        new GetProductDTO(
            UUID.randomUUID(),
            "shoes",
            "Adidas",
            LocalDate.parse("2020-05-15"),
            null,
            Category.CLOTHES,
            "This shoes is a sporty shoes",
            new BigDecimal(50)
        );

    public static GetProductDTO GET_SAMSUNG_PRODUCT_DTO =
        new GetProductDTO(
            UUID.randomUUID(),
            "Watch 5",
            "SAMSUNG",
            LocalDate.parse("2022-10-15"),
            null,
            Category.APPLIANCES,
            "This is a smart watch device",
            new BigDecimal(200)
        );
}
