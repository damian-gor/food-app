package com.damgor.foodapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Data
@Entity
@NoArgsConstructor
public class Meal extends RepresentationModel<Meal> {

    @EmbeddedId
    private MealId id;
    @ElementCollection
    private Map<String, Integer> elements;
    private Double mealKcal=0.0;
    private Double mealProtein=0.0;
    private Double mealCarbs=0.0;
    private Double mealFat=0.0;
    @Embedded
    @ElementCollection
    private List<MealProduct> products;

    public Meal(Long profileId, Date date) {
        this.id = new MealId(profileId, date);
    }

    public Meal(Long profileId, Date date, Integer mealNumber, Map<String, Integer> elements) {
        if (date == null) date = new java.sql.Date(System.currentTimeMillis());
        this.id = new MealId(profileId, date, mealNumber);
        this.elements = elements;
    }

    public void setProducts(List<MealProduct> products) {
        this.products = products;
        this.mealKcal = 0.0;
        this.mealProtein = 0.0;
        this.mealCarbs = 0.0;
        this.mealFat = 0.0;

        for (MealProduct product : products) {
            mealKcal += product.getProductKcal();
            mealProtein += round(product.getProductProtein());
            mealCarbs += product.getProductProtein();
            mealFat += product.getProductFat();
        }

        this.mealKcal = round(mealKcal);
        this.mealProtein = round(mealProtein);
        this.mealCarbs = round(mealCarbs);
        this.mealFat = round(mealFat);
    }

    public Double getNutrientSummary(String nutrient) {
        AtomicReference<Double> result = new AtomicReference<>(0.0);
        switch (nutrient) {
            case "kcal":
                products.forEach(p -> result.updateAndGet(v -> v + p.getProductKcal()));
                break;
            case "protein":
                products.forEach(p -> result.updateAndGet(v -> v + p.getProductProtein()));
                break;
            case "carbs":
                products.forEach(p -> result.updateAndGet(v -> v + p.getProductCarbs()));
                break;
            case "fat":
                products.forEach(p -> result.updateAndGet(v -> v + p.getProductFat()));
                break;
        }
        return round(result.get());
    }

    private Double round(Double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}

