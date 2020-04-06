package com.damgor.foodapp.service;

import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.model.ShortRecipe;

import java.util.List;

public interface RecipeService {

    Recipe getRandomRecipe();
    Recipe getRecipeById(int id);
    List<ShortRecipe> getRecipesByIngredients(String ingredients);
    List<ShortRecipe> getRecipesByText(String text);
    List<ShortRecipe> getMostSuitableRecipes(long profileId, int number, int offset);
    List<ShortRecipe> getCompromiseRecipes(String profileIds, int number, int offset);
}
