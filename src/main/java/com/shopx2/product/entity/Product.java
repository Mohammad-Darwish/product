package com.shopx2.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class Product {

    // Since we are not inserting a patch then Identity will work fine, and we don't need sequence.
    // assign the id to the entity, by DB, at insertion time.
    //    @GeneratedValue(strategy = GenerationType.AUTO) This work but it does things implicitly so it might change
    //    with the version of the provider but both works and both java is generating the value not DB
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String brandName;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate productionDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Category category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;
}
