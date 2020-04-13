package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.ProductController;
import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.model.*;
import com.damgor.foodapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@CacheConfig(cacheNames = {"cacheProducts"})
public class ProductServiceImpl implements ProductService {

    private List<Product> cacheProducts = new ArrayList<>();

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
        List<ShortProduct> products = new ArrayList<>();
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
    @Cacheable
    public Product getGeneralProducts(String productName) {
        EdamamOutput output = restTemplate.getForObject(
                "https://api.edamam.com/api/food-database/parser?ingr="
                        + productName
                        + "&app_id=" + edamamAppId
                        + "&app_key=" + edamamAppKey,
                EdamamOutput.class);

        Product product = new Product(
                output.getParsed().get(0).getFood().getFoodId(),
                output.getParsed().get(0).getFood().getLabel(),
                output.getParsed().get(0).getFood().getNutrients().getKcal(),
                output.getParsed().get(0).getFood().getNutrients().getProtein(),
                output.getParsed().get(0).getFood().getNutrients().getCarbs(),
                output.getParsed().get(0).getFood().getNutrients().getFat()
        );
        product.add(
                linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProfileController.class).addToFavourites(999999999, null, product.getId()))
                        .withRel("Add(/remove) product to favourites (insert profileId)"));

        return product;
    }

    @Override
    @Cacheable
    public Product getProductById(String productId) {
        if (productId.length() > 10) {
            return getGeneralProducts(productId);
        } else {
            SpecificProduct output = restTemplate.getForObject(
                    "https://api.spoonacular.com/food/products/"
                            + productId
                            + "?apiKey=" + spoonacularApiKey,
                    SpecificProduct.class);
            Product product = new Product(
                    output.getId().toString(),
                    output.getProductName(),
                    output.getNutrition().getKcal(),
                    output.getNutrition().getProtein(),
                    output.getNutrition().getCarbs(),
                    output.getNutrition().getFat()
            );
            product.add(linkTo(methodOn(ProductController.class).getProductById(productId)).withSelfRel(),
                    linkTo(methodOn(ProfileController.class).addToFavourites(999999999, null, product.getId()))
                            .withRel("Add(/remove) product to favourites (insert profileId)"));

            return product;
        }
    }

}
