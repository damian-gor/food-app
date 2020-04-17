package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Product;
import com.damgor.foodapp.model.ShortProduct;
import com.damgor.foodapp.repository.UserRepository;
import com.damgor.foodapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search-specific/{productName}")
    public ResponseEntity<List<ShortProduct>> getSpecificProducts(@RequestParam(defaultValue = "5") int number,
                                                                  @RequestParam(defaultValue = "0") int offset,
                                                                  @PathVariable String productName,
                                                                  Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(productService.getSpecificProducts(productName, number, offset, userId));
    }

    @GetMapping("/search-general/{productName}")
    public ResponseEntity<Product> getGeneralProducts(@PathVariable String productName,
                                                      Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(productService.getGeneralProducts(productName, userId));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId,
                                                  Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(productService.getProductById(productId, userId));
    }


    /////////////// SUPPORTING METHODS /////////////

    private long getProfileIdIfAuthenticated(Principal principal) {
        long userId = 99999;
        if (principal != null) userId = userRepository.getUserIdByUserName(principal.getName());
        return userId;
    }
}
