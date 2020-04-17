package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.model.ShortRecipe;
import com.damgor.foodapp.repository.UserRepository;
import com.damgor.foodapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/random")
    public ResponseEntity<Recipe> getRandomRecipe(Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getRandomRecipe(userId));
    }

    @GetMapping("/byIngredients/{ingredients}")
    public ResponseEntity<List<ShortRecipe>> getRecipesByIngredients(@PathVariable("ingredients") String ingredients, Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getRecipesByIngredients(ingredients, userId));
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("recipeId") int recipeId, Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getRecipeById(recipeId, userId));
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<List<ShortRecipe>> getRecipesByText(@PathVariable("text") String text, Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getRecipesByText(text, userId));
    }

    @GetMapping("/suitable/{profileId}")
    public ResponseEntity<List<ShortRecipe>> getMostSuitableRecipes(@RequestParam(defaultValue = "5") int number,
                                                                    @RequestParam(defaultValue = "0") int offset,
                                                                    @PathVariable("profileId") long profileId,
                                                                    Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getMostSuitableRecipes(profileId, number, offset, userId));
    }

    @GetMapping("/compromise")
    public ResponseEntity<List<ShortRecipe>> getCompromiseRecipes(@RequestParam(defaultValue = "5") int number,
                                                                  @RequestParam(defaultValue = "0") int offset,
                                                                  @RequestParam("id") String profileIds,
                                                                  Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getCompromiseRecipes(profileIds, number, offset, userId));
    }

    /////////////// SUPPORTING METHODS /////////////

    private long getProfileIdIfAuthenticated(Principal principal) {
        long userId = 99999;
        if (principal != null) userId = userRepository.getUserIdByUserName(principal.getName());
        return userId;
    }
}
