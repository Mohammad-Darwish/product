package com.gfsp.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gfsp.product.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDTO {

    @NotNull(message = "Id must not be null")
    private UUID id;

    @NotEmpty(message = "Product name must not be null or empty")
    private String productName;
    @NotEmpty(message = "Brand name must not be null or empty")
    private String brandName;
    @NotNull(message = "Production Date must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate productionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    @NotNull(message = "Category must not be null")
    private Category category;
    @NotEmpty(message = "Description name must not be null or empty")
    private String description;
    @NotNull(message = "Price must not be null")
    private BigDecimal price;
    @NotNull(message = "Quantity must not be null")
    private Integer quantity;
}
