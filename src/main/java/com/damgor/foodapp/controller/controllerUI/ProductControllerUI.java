package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.Product;
import com.damgor.foodapp.model.ShortProduct;
import com.damgor.foodapp.repository.UserRepository;
import com.damgor.foodapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/ui/products")
@ApiIgnore
public class ProductControllerUI {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search-specific/{productName}")
    public ModelAndView getSpecificProducts(@RequestParam(defaultValue = "5") int number,
                                      @RequestParam(defaultValue = "0") int offset,
                                      @PathVariable String productName,
                                      Principal principal) {
        ModelAndView modelAndView = new ModelAndView("products");
        long userId = getProfileIdIfAuthenticated(principal);
        List<ShortProduct> products = productService.getSpecificProducts(productName, number, offset, userId);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/search-general/{productName}")
    public ModelAndView getGeneralProduct(@PathVariable String productName,
                                          Principal principal) {
        ModelAndView modelAndView = new ModelAndView("product");
        long userId = getProfileIdIfAuthenticated(principal);
        Product product = productService.getGeneralProducts(productName, userId);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping("/{productId}")
    public ModelAndView getProductById(@PathVariable String productId,
                                                  Principal principal) {
        ModelAndView modelAndView = new ModelAndView("product");
        long userId = getProfileIdIfAuthenticated(principal);
        Product product = productService.getProductById(productId, userId);
        modelAndView.addObject("product", product);
        return modelAndView;
    }


    /////////////// SUPPORTING METHODS /////////////

    private long getProfileIdIfAuthenticated(Principal principal) {
        long userId = 99999;
        if (principal != null) userId = userRepository.getUserIdByUserName(principal.getName());
        return userId;
    }
}
