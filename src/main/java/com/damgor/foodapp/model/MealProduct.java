package com.damgor.foodapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class MealProduct extends RepresentationModel<MealProduct> {

    private String productId;
    private String productName;
    private Integer grams;
    private Double productKcal;
    private Double productProtein;
    private Double productCarbs;
    private Double productFat;

    public MealProduct(Product product, Integer grams) {
        Double scale = Double.valueOf(grams) / 100;
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.grams = grams;
        this.productKcal = round(product.getKcal()*scale);
        this.productProtein = round(product.getProtein()*scale);
        this.productCarbs = round(product.getCarbs()*scale);
        this.productFat = round(product.getFat()*scale);
    }

    private Double round (Double d){
        return Math.round(d * 100.0) / 100.0;
    }
}
