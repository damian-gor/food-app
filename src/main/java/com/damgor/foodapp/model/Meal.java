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

@Data
@Entity
@NoArgsConstructor
public class Meal extends RepresentationModel<Meal> {

    @EmbeddedId
    private MealId id;
    @ElementCollection
//    @JsonIgnore
    private Map<String, Integer> elements;
    private Double mealKcal;
    private Double mealProtein;
    private Double mealCarbs;
    private Double mealFat;
    @Embedded
    @ElementCollection
    private List<MealProduct> products;


    public Meal(Long profileId, Date date) {
        this.id = new MealId(profileId, date);
    }


    public Meal(DiaryPage dp, Integer mealNumber, Map<String, Integer> elements) {
        this.id = new MealId(dp.getId().getProfileId(), dp.getId().getDate(), mealNumber);
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

    private Double round(Double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}

