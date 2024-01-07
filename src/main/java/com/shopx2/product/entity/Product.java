package com.shopx2.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {

    @Id
    private UUID id;

    @NotNull
    private String productName;
    @NotNull
    private String brandName;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate productionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    @NotNull
    private Category category;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", productName='" + productName + '\'' +
            ", brandName='" + brandName + '\'' +
            ", productionDate=" + productionDate +
            ", expirationDate=" + expirationDate +
            ", category=" + category +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            '}';
    }
}
