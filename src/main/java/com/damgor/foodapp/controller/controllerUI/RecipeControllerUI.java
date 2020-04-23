package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.model.ShortRecipe;
import com.damgor.foodapp.repository.UserRepository;
import com.damgor.foodapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/ui/recipes")
@ApiIgnore
public class RecipeControllerUI {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/random")
    public ModelAndView getRandomRecipe(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("recipe");
        long userId = getProfileIdIfAuthenticated(principal);
        Recipe recipe = recipeService.getRandomRecipe(userId);
        modelAndView.addObject("recipe", recipe);
        return modelAndView;
    }

    @GetMapping("/byIngredients/{ingredients}")
    public ModelAndView getRecipesByIngredients(@PathVariable("ingredients") String ingredients, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("recipes-ingr");
        long userId = getProfileIdIfAuthenticated(principal);
        List<ShortRecipe> recipes = recipeService.getRecipesByIngredients(ingredients, userId);
        modelAndView.addObject("recipes", recipes);
        return modelAndView;
    }

    @GetMapping("/{recipeId}")
    public ModelAndView getRecipeById(@PathVariable("recipeId") int recipeId, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("recipe");
        long userId = getProfileIdIfAuthenticated(principal);
        Recipe recipe = recipeService.getRecipeById(recipeId, userId);
        modelAndView.addObject("recipe", recipe);
        return modelAndView;
    }

    @GetMapping("/search/{text}")
    public ModelAndView getRecipesByText(@PathVariable("text") String text, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("recipes");
        long userId = getProfileIdIfAuthenticated(principal);
        List<ShortRecipe> recipes =recipeService.getRecipesByText(text, userId);
        modelAndView.addObject("recipes", recipes);
        return modelAndView;
    }

    @GetMapping("/suitable/{profileId}")
    public ModelAndView getMostSuitableRecipes(@RequestParam(defaultValue = "5") int number,
                                                                    @RequestParam(defaultValue = "0") int offset,
                                                                    @PathVariable("profileId") long profileId,
                                                                    Principal principal) {
        ModelAndView modelAndView = new ModelAndView("recipes");
        long userId = getProfileIdIfAuthenticated(principal);
        List<ShortRecipe> recipes =recipeService.getMostSuitableRecipes(profileId, number, offset, userId);
        modelAndView.addObject("recipes", recipes);
        return modelAndView;
    }

    @GetMapping("/compromise")
    public ModelAndView getCompromiseRecipes(@RequestParam(defaultValue = "5") int number,
                                                                  @RequestParam(defaultValue = "0") int offset,
                                                                  @RequestParam("id") String profileIds,
                                                                  Principal principal) {
        ModelAndView modelAndView = new ModelAndView("recipes");
        long userId = getProfileIdIfAuthenticated(principal);
        List<ShortRecipe> recipes =recipeService.getCompromiseRecipes(profileIds, number, offset, userId);
        modelAndView.addObject("recipes", recipes);
        return modelAndView;
    }

    /////////////// SUPPORTING METHODS /////////////

    private long getProfileIdIfAuthenticated(Principal principal) {
        long userId = 99999;
        if (principal != null) userId = userRepository.getUserIdByUserName(principal.getName());
        return userId;
    }
}
