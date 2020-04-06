package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.ProductController;
import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.model.*;
import com.damgor.foodapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spoonacular.apiKey}")
    private String spoonacularApiKey;

    @Value("${edamam.app_id}")
    private String edamamAppId;

    @Value("${edamam.app_key}")
    private String edamamAppKey;


    @Override
    public List<ShortProduct> getSpecificProducts(String productName, int number, int offset) {
        List <ShortProduct> products = new ArrayList<>();
        SpoonacularOutput output = restTemplate.getForObject(
                "https://api.spoonacular.com/food/products/search?query="
                        + productName
                        + "&apiKey=" + spoonacularApiKey,
                SpoonacularOutput.class);

        products.addAll(output.getProducts());
        products.stream().forEach(product -> product.add(
                linkTo(methodOn(ProductController.class).getProductById(product.getId().toString())).withRel("Get product details")
        ));
        return products;
    }

    @Override
    public Product getGeneralProducts(String productName) {
        EdamamOutput output = restTemplate.getForObject(
                "https://api.edamam.com/api/food-database/parser?ingr="
                + productName
                + "&app_id=" + edamamAppId
                + "&app_key=" + edamamAppKey,
                EdamamOutput.class);

        return new Product(
                output.getParsed().get(0).getFood().getFoodId(),
                output.getParsed().get(0).getFood().getLabel(),
                output.getParsed().get(0).getFood().getNutrients().getKcal(),
                output.getParsed().get(0).getFood().getNutrients().getProtein(),
                output.getParsed().get(0).getFood().getNutrients().getCarbs(),
                output.getParsed().get(0).getFood().getNutrients().getFat()
        );
    }

    @Override
    public Product getProductById(String productId) {
        if(productId.length()>10){
            return null;
        }
        else {
            SpecificProduct output = restTemplate.getForObject(
                    "https://api.spoonacular.com/food/products/"
                            + productId
                            + "?apiKey=" + spoonacularApiKey,
                    SpecificProduct.class);
            return new Product(
                    output.getId().toString(),
                    output.getProductName(),
                    output.getNutrition().getKcal(),
                    output.getNutrition().getProtein(),
                    output.getNutrition().getCarbs(),
                    output.getNutrition().getFat()
            );
        }
    }

    @Override
    public Message addToFavourites(String productId) {
        return null;
    }
}
