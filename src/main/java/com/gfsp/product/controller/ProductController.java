package com.gfsp.product.controller;

import com.gfsp.product.dto.ProductDTO;
import com.gfsp.product.entity.Category;
import com.gfsp.product.service.Impl.ProductServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(
    path = "api/v1/products"
)
@AllArgsConstructor
@RequiredArgsConstructor
@Validated
public class ProductController {

    @Autowired
    private ProductServiceImp productService;

    @Operation(
        summary = "ADD products rest API",
        description = "ADD products endpoint to add products to the database")
    @ApiResponse(
        responseCode = "201",
        description = "HTTP status is 201 created")
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ProductDTO>> addProducts(@Valid @RequestBody
                                                        List<ProductDTO> productDTOS) {
        List<ProductDTO> createdProducts = productService.addProducts(productDTOS);
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }

    @Operation(
        summary = "GET product by id restAPI",
        description = "GET product by id to get product id that matches the path variable")
    @ApiResponse(
        responseCode = "200",
        description = "HTTP status code is 200 okay")
    @GetMapping(
        path = "{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductDTO> getProductById(@Valid @PathVariable
                                                     String id) {
        ProductDTO productByID = productService.getProductByID(id);
        return new ResponseEntity<>(productByID, HttpStatus.OK);
    }

    @Operation(
        summary = "GET products restAPI",
        description = "GET products to get all products with possibility to add filters using parameters"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP status code is 200 okay"
    )
    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ProductDTO>> getProducts(@Valid @RequestParam(required = false) List<Category> category,
                                                        @Valid @RequestParam(required = false) BigDecimal minValue,
                                                        @Valid @RequestParam(required = false) BigDecimal maxValue) {
        List<ProductDTO> products = productService.getProducts(category, minValue, maxValue);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(
        summary = "DELETE product by ID restAPI",
        description = "DELETE product by ID is to remove a product from the database."
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP status code is 200 okay"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@Valid @PathVariable
                                                    String id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
