package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.model.ShortRecipe;
import com.damgor.foodapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @GetMapping("/random")
    public ResponseEntity<Recipe> getRandomRecipe() {
        return ResponseEntity.ok().body(recipeService.getRandomRecipe());
    }

    @GetMapping("/byIngredients/{ingredients}")
    public ResponseEntity<List<ShortRecipe>> getRecipesByIngredients(@PathVariable("ingredients") String ingredients) {
        return ResponseEntity.ok().body(recipeService.getRecipesByIngredients(ingredients));
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("recipeId") int recipeId) {
        return ResponseEntity.ok().body(recipeService.getRecipeById(recipeId));
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<List<ShortRecipe>> getRecipesByText(@PathVariable("text") String text) {
        return ResponseEntity.ok().body(recipeService.getRecipesByText(text));
    }

    @GetMapping("/suitable/{profileId}")
    public ResponseEntity<List<ShortRecipe>> getMostSuitableRecipes(@PathVariable("profileId") long profileId){
        return ResponseEntity.ok().body(recipeService.getMostSuitableRecipes(profileId));
    }
}
