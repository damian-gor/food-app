package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Product;
import com.damgor.foodapp.model.ShortProduct;
import com.damgor.foodapp.repository.UserRepository;
import com.damgor.foodapp.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/products")
@Api(tags = "1. Products", description = "Search for food products by name or ID. General products like 'milk', 'eggs', " +
        "or specific, like 'Snickers Candy Bars'. Products can also be saved as favorites for a given profile.")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get matching products list",
            notes = "Finds products of specific brands that match the query. You can define the number of displayed products. " +
                    "Then you can go to the details of the selected product by clicking on the link. The product can be added " +
                    "to the list of favorite products of a given profile.",
            response = ShortProduct.class)
    @GetMapping("/search-specific/{productName}")
    public ResponseEntity<List<ShortProduct>> getSpecificProducts(@RequestParam(defaultValue = "5") int number,
                                                                  @RequestParam(defaultValue = "0") int offset,
                                                                  @PathVariable String productName,
                                                                  Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(productService.getSpecificProducts(productName, number, offset, userId));
    }

    @ApiOperation(value = "Get general product",
            notes = "Finds the product of general type, like 'milk' or 'eggs'. The product can be added " +
                    "to the list of favorite products of a given profile.",
            response = Product.class)
    @GetMapping("/search-general/{productName}")
    public ResponseEntity<Product> getGeneralProduct(@PathVariable String productName,
                                                     Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(productService.getGeneralProducts(productName, userId));
    }

    @ApiOperation(value = "Get product by id",
            notes = "The product can be added to the list of favorite products of a given profile.",
            response = Product.class)
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
