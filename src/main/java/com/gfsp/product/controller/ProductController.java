package com.gfsp.product.controller;

import com.gfsp.product.dto.ProductDTO;
import com.gfsp.product.service.Impl.ProductServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
@RequiredArgsConstructor
@Validated
public class ProductController {

    @Autowired
    private ProductServiceImp productService;

    @PostMapping
    public ResponseEntity<List<ProductDTO>> addProducts(@Valid @RequestBody List<ProductDTO> productDTOS) {
        List<ProductDTO> createdProducts = productService.addProducts(productDTOS);
        return new ResponseEntity<>(createdProducts,HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@Valid @PathVariable String id){
        ProductDTO productByID = productService.getProductByID(id);
        return new ResponseEntity<>(productByID, HttpStatus.OK);
    }

    @GetMapping( params = "productName", produces ={"application/json","application/xml"})
    public ResponseEntity<List<ProductDTO>> getProductByName(@Valid @RequestParam String productName){
        List<ProductDTO> productByID = productService.getProductsByName(productName);
        return new ResponseEntity<>(productByID, HttpStatus.OK);
    }
}
