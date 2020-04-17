package com.damgor.foodapp.service;

import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.model.ShortRecipe;

import java.util.List;

public interface RecipeService {
    Recipe getRandomRecipe(long userId);
    Recipe getRecipeById(int id, long userId);
    List<ShortRecipe> getRecipesByIngredients(String ingredients, long userId);
    List<ShortRecipe> getRecipesByText(String text, long userId);
    List<ShortRecipe> getMostSuitableRecipes(long profileId, int number, int offset, long userId);
    List<ShortRecipe> getCompromiseRecipes(String profileIds, int number, int offset, long userId);
}
