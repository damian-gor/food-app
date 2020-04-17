package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Product;
import com.damgor.foodapp.model.ShortProduct;
import com.damgor.foodapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search-specific/{productName}")
    public ResponseEntity<List<ShortProduct>> getSpecificProducts (@RequestParam(defaultValue = "5") int number,
                                                           @RequestParam(defaultValue = "0") int offset,
                                                           @PathVariable String productName) {
        return ResponseEntity.ok().body(productService.getSpecificProducts(productName, number, offset));
    }

    @GetMapping("/search-general/{productName}")
    public ResponseEntity<Product> getGeneralProducts (@PathVariable String productName) {
        return ResponseEntity.ok().body(productService.getGeneralProducts(productName));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById (@PathVariable String productId) {
        return ResponseEntity.ok().body(productService.getProductById(productId));
    }


}
