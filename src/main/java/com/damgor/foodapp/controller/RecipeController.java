package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.model.ShortRecipe;
import com.damgor.foodapp.repository.UserRepository;
import com.damgor.foodapp.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/recipes")
@Api(tags = "2. Recipes", description = "Search for recipes by text, ingredients or id. Possibility to draw a recipe, search" +
        " for a recipe corresponding to a given profile or a recipe corresponding to a selected group of profiles.")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get a random recipe",
            response = Recipe.class)
    @GetMapping("/random")
    public ResponseEntity<Recipe> getRandomRecipe(Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getRandomRecipe(userId));
    }

    @ApiOperation(value = "Get a list of recipes, containing specific ingredients",
            notes = "Enter the desired ingredients, separating them with a comma or space.",
            response = ShortRecipe.class)
    @GetMapping("/byIngredients/{ingredients}")
    public ResponseEntity<List<ShortRecipe>> getRecipesByIngredients(@RequestParam(defaultValue = "5") int number,
                                                                     @PathVariable("ingredients") String ingredients, Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getRecipesByIngredients(ingredients, userId, number));
    }

    @ApiOperation(value = "Get the recipe by id",
            response = Recipe.class)
    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("recipeId") int recipeId, Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getRecipeById(recipeId, userId));
    }

    @ApiOperation(value = "Get a list of recipes that match the description you entered",
            notes = "E.g. 'Cake with carrots without nuts'.",
            response = ShortRecipe.class)
    @GetMapping("/search/{text}")
    public ResponseEntity<List<ShortRecipe>> getRecipesByText(@RequestParam(defaultValue = "5") int number,
                                                              @RequestParam(defaultValue = "0") int offset,
                                                              @PathVariable("text") String text,
                                                              Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getRecipesByText(text, userId, number, offset));
    }

    @ApiOperation(value = "Receive a list of recipes that match the nutritional preferences of the indicated profile",
            notes = "Insert profileId. You can define the number of displayed products.",
            response = ShortRecipe.class)
    @GetMapping("/suitable/{profileId}")
    public ResponseEntity<List<ShortRecipe>> getMostSuitableRecipes(@RequestParam(defaultValue = "5") int number,
                                                                    @RequestParam(defaultValue = "0") int offset,
                                                                    @PathVariable("profileId") long profileId,
                                                                    Principal principal) {
        long userId = getProfileIdIfAuthenticated(principal);
        return ResponseEntity.ok().body(recipeService.getMostSuitableRecipes(profileId, number, offset, userId));
    }

    @ApiOperation(value = "Receive a list of recipes that match the nutritional preferences of the indicated profiles",
            notes = "Insert any number of profile' ids, separating them with a comma. You can define the number of displayed recipes.",
            response = ShortRecipe.class)
    @GetMapping("/compromise/{profileIds}")
    public ResponseEntity<List<ShortRecipe>> getCompromiseRecipes(@RequestParam(defaultValue = "5") int number,
                                                                  @RequestParam(defaultValue = "0") int offset,
                                                                  @PathVariable("profileIds") String profileIds,
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
