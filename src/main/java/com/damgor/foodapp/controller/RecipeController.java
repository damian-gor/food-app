package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.model.ShortRecipe;
import com.damgor.foodapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

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
    public ResponseEntity<List<ShortRecipe>> getMostSuitableRecipes(@RequestParam(defaultValue = "5") int number,
                                                                    @RequestParam(defaultValue = "0") int offset,
                                                                    @PathVariable("profileId") long profileId) {
        return ResponseEntity.ok().body(recipeService.getMostSuitableRecipes(profileId, number, offset));
    }

    @GetMapping("/compromise")
    public ResponseEntity<List<ShortRecipe>> getCompromiseRecipes(@RequestParam(defaultValue = "5") int number,
                                                                  @RequestParam(defaultValue = "0") int offset,
                                                                  @RequestParam("id") String profileIds) {
        return ResponseEntity.ok().body(recipeService.getCompromiseRecipes(profileIds, number, offset));
    }
}
