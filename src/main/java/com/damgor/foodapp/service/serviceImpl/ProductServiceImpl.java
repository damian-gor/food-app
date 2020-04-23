package com.damgor.foodapp.service.serviceImpl;

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

@Service
@CacheConfig(cacheNames = {"cacheProducts"})
public class ProductServiceImpl implements ProductService {

    private List<Product> cacheProducts = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LinkProvider linkProvider;

    @Value("${spoonacular.apiKey}")
    private String spoonacularApiKey;
    @Value("${edamam.app_id}")
    private String edamamAppId;
    @Value("${edamam.app_key}")
    private String edamamAppKey;


    @Override
    public List<ShortProduct> getSpecificProducts(String productName, int number, int offset, long profileId) {
        List<ShortProduct> products = new ArrayList<>();
        SpoonacularOutput output = restTemplate.getForObject(
                "https://api.spoonacular.com/food/products/search?query="
                        + productName
                        + "&apiKey=" + spoonacularApiKey,
                SpoonacularOutput.class);

        products.addAll(output.getProducts());
        linkProvider.addProductsLinks(products,profileId);
        return products;
    }

    @Override
    @Cacheable
    public Product getGeneralProducts(String productName, long userId) {
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
        linkProvider.addProductsLinks(product,userId);
        return product;
    }

    @Override
    @Cacheable
    public Product getProductById(String productId, long userId) {
        if (productId.length() > 10) {
            return getGeneralProducts(productId, userId);
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
            linkProvider.addProductsLinks(product, userId);
            return product;
        }
    }
}
