package com.shopx2.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopx2.product.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateProductDTO(
    @NotEmpty(message = "Product name must not be null or empty")
    String productName,
    @NotEmpty(message = "Brand name must not be null or empty")
    String brandName,
    @NotNull(message = "Production Date must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate productionDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate expirationDate,
    @NotNull(message = "Category must not be null")
    Category category,
    @NotEmpty(message = "Description name must not be null or empty")
    String description,
    @NotNull(message = "Price must not be null")
    BigDecimal price
) {
}
