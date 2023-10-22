package com.gfsp.product.utils;

import com.gfsp.product.dto.ProductDTO;
import com.gfsp.product.entity.Category;
import com.gfsp.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TestUtils {
    public static Product IPHONE_PRODUCT =
        new Product(UUID.randomUUID(),
            "IPHONE 14",
            "IPHONE",
            LocalDate.parse("2022-05-15"),
            null,
            Category.APPLIANCES,
            "This is a phone device",
            new BigDecimal(900),
            200);

    public static Product SHOES_PRODUCT =
        new Product(UUID.randomUUID(),
            "shoes",
            "Adidas",
            LocalDate.parse("2020-05-15"),
            null,
            Category.CLOTHES,
            "This shoes is a sporty shoes",
            new BigDecimal(50),
            100);

    public static Product SAMSUNG_PRODUCT =
        new Product(UUID.randomUUID(),
            "Watch 5",
            "SAMSUNG",
            LocalDate.parse("2022-10-15"),
            null,
            Category.APPLIANCES,
            "This is a smart watch device",
            new BigDecimal(200),
            50);


    public static ProductDTO IPHONE_PRODUCT_DTO =
        ProductDTO.builder()
            .id(UUID.randomUUID())
            .productName("IPHONE 14")
            .brandName("IPHONE")
            .productionDate(LocalDate.parse("2022-05-15"))
            .expirationDate(null)
            .category(Category.APPLIANCES)
            .description("This is a phone device")
            .price(new BigDecimal(900))
            .quantity(200)
            .build();

    public static ProductDTO INVALID_PRODUCT_DTO =
        ProductDTO.builder()
            .id(UUID.randomUUID())
            .expirationDate(null)
            .category(Category.APPLIANCES)
            .description("This is a phone device")
            .price(new BigDecimal(900))
            .quantity(200)
            .build();

    public static ProductDTO SHOES_PRODUCT_DTO =
        ProductDTO.builder()
            .id(UUID.randomUUID())
            .productName("shoes")
            .brandName("Adidas")
            .productionDate(LocalDate.parse("2020-05-15"))
            .expirationDate(null)
            .category(Category.CLOTHES)
            .description("This shoes is a sporty shoes")
            .price(new BigDecimal(50))
            .quantity(100)
            .build();

    public static ProductDTO SAMSUNG_PRODUCT_DTO =
        ProductDTO.builder()
            .id(UUID.randomUUID())
            .productName("Watch 5")
            .brandName("SAMSUNG")
            .productionDate(LocalDate.parse("2022-10-15"))
            .expirationDate(null)
            .category(Category.APPLIANCES)
            .description("This is a smart watch device")
            .price(new BigDecimal(200))
            .quantity(50)
            .build();
}
