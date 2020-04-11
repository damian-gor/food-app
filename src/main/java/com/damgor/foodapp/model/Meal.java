package com.damgor.foodapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Meal extends RepresentationModel<Meal> {


    @EmbeddedId
    private MealId id;
    @ElementCollection
    private Map<Integer, Integer> elements;
    //    private Map<Diarable,Integer> elements;
    private Double kcal;
    private Double protein;
    private Double carbs;
    private Double fat;


    //    wszystkie parametry
    public Meal(Long profileId, Date date, Integer mealNumber, Map<Integer, Integer> elements, Double kcal, Double protein, Double carbs, Double fat) {
        this.id = new MealId(profileId, date, mealNumber);
        this.elements = elements;
        this.kcal = kcal;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    //    po id Diary page
    public Meal(DiaryPage dp, Integer mealNumber, Map<Integer, Integer> elements, Double kcal, Double protein, Double carbs, Double fat) {
        this.id = new MealId(dp.getId().getProfileId(), dp.getId().getDate(), mealNumber);
        ;
        this.elements = elements;
        this.kcal = kcal;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

}

