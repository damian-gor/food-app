package com.damgor.foodapp.service;

import com.damgor.foodapp.model.Meal;
import com.damgor.foodapp.model.Message;

import java.util.List;

public interface MealService {
    List<Meal> getAllMeals(Long profileId, String stringDate);
    Meal getMeal(Long profileId, String stringDate, Integer mealId);
    Meal addMeal(Meal meal);
    Meal updateMeal(Meal meal);
    Meal partialUpdateMeal(Meal meal);
    Message removeMeal(Long profileId, String stringDate, Integer mealId);
}
