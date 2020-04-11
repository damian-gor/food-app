package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.controller.RecipeController;
import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.*;
import com.damgor.foodapp.service.ProfileService;
import com.damgor.foodapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@CacheConfig(cacheNames={"cacheRecipes"})
public class RecipeServiceImpl implements RecipeService {

//    wyrzucic exception dla wszystkich: {
//    "status": "failure",
//    "code": 402,
//    "message": "Your daily points limit of 150 has been reached. Please upgrade your plan to continue using the API."
//} status: 402 Payment Required

    private List<Recipe> cacheRecipes = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProfileService profileService;

    @Value("${spoonacular.apiKey}")
    private String apiKey;

    @Override
    public Recipe getRandomRecipe() {
        SpoonacularOutput output = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/random?"
                        + "apiKey=" + apiKey,
                SpoonacularOutput.class);
        Recipe recipe = output.getRecipes().get(0);

        recipe.add(
                linkTo(methodOn(RecipeController.class).getRecipeById(recipe.getId())).withSelfRel(),
                linkTo(methodOn(ProfileController.class).addToFavourites(999999999, recipe.getId().toString(), null))
                        .withRel("Add(/remove) recipe to favourites (insert profileId)"));
        return recipe;
    }

    @Override
    public List<ShortRecipe> getRecipesByIngredients(String ingredients) {
        ShortRecipe[] output = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/findByIngredients?"
                        + "ranking=1"
                        + "&ingredients=" + ingredients
                        + "&apiKey=" + apiKey,
                ShortRecipe[].class);
        List<ShortRecipe> recipes = Arrays.asList(output);
        recipes.forEach(r -> r.add(
                linkTo(methodOn(RecipeController.class).getRecipeById(r.getId())).withRel("Get recipe details")
        ));

        return recipes;
    }

    //    Potem ogarnać error handling dla 404
    @Override
    @Cacheable
    public Recipe getRecipeById(int id) {
        try {
            Recipe recipe = restTemplate.getForObject(
                    "https://api.spoonacular.com/recipes/"
                            + id
                            + "/information?"
                            + "apiKey=" + apiKey,
                    Recipe.class);
            recipe.add(
                    linkTo(methodOn(RecipeController.class).getRecipeById(recipe.getId())).withSelfRel(),
                    linkTo(methodOn(ProfileController.class).addToFavourites(999999999, recipe.getId().toString(), null))
                            .withRel("Add(/remove) recipe to favourites (insert profileId)"));
            return recipe;
        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException(Recipe.class, "id", String.valueOf(id));
        }
    }

    @Override
    public List<ShortRecipe> getRecipesByText(String text) {
        String query = text.replaceAll(" ", "+");
        List<ShortRecipe> recipes = new ArrayList<>();
        SpoonacularOutput output = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/search"
                        + "?query=" + query
                        + "&apiKey=" + apiKey,
                SpoonacularOutput.class);
        recipes.addAll(output.getResults());
        if (!recipes.isEmpty()) {
            recipes.forEach(r -> r.add(
                    linkTo(methodOn(RecipeController.class).getRecipeById(r.getId())).withRel("Get recipe details")
            ));
            return recipes;
        } else throw new EntityNotFoundException(ShortRecipe.class, "query", text);
    }

    @Override
    public List<ShortRecipe> getMostSuitableRecipes(long profileId, int number, int offset) {
        Profile profile = profileService.getProfile(profileId);
        String query = "";

        if (profile.getCuisine() != null) query += "cuisine=" + profile.getCuisine();
        if (profile.getDiet() != null) query += "&diet=" + profile.getDiet();
        if (profile.getIntolerance() != null) query += "&intolerances=" + profile.getIntolerance();
        if (!query.isEmpty()) query += "&";

        List<ShortRecipe> recipes = new ArrayList<>();
        SpoonacularOutput output = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/complexSearch?"
                        + query
                        + "sort=meta-score&sortDirection=desc"
                        + "&number=" + number
                        + "&offset=" + offset
                        + "&apiKey=" + apiKey,
                SpoonacularOutput.class);
        recipes.addAll(output.getResults());
        recipes.forEach(r -> r.add(
                linkTo(methodOn(RecipeController.class).getRecipeById(r.getId())).withRel("Get recipe details")
        ));

        return recipes;
    }

    @Override
    public List<ShortRecipe> getCompromiseRecipes(String profileIds, int number, int offset) {
        List<Long> profileIdList = Arrays.stream(profileIds.split(",")).map(Long::parseLong).collect(Collectors.toList());

        String cuisines = null;
        String diets = null;
        String intolerances = null;

        for (Long profileId : profileIdList) {
            Profile profile = profileService.getProfile(profileId);

            if (profile.getCuisine() != null) {
                if (cuisines == null) cuisines = "cuisine=";
                if (!cuisines.contains(profile.getCuisine().toString())) {
                    if (cuisines.length() > 8) cuisines += ",";
                    cuisines += profile.getCuisine();
                }
            }
            if (profile.getDiet() != null) {
                if (diets == null) diets = "&diet=";
                if (!diets.contains(profile.getDiet().toString())) {
                    if (diets.length() > 6) diets += ",";
                    diets += profile.getDiet();
                }
            }
            if (profile.getIntolerance() != null) {
                if (intolerances == null) intolerances = "&intolerances=";
                if (!intolerances.contains(profile.getIntolerance().toString())) {
                    if (intolerances.length() > 14) intolerances += ",";
                    intolerances += profile.getIntolerance();
                }
            }
        }

        String query = cuisines + diets + intolerances;
        if (!query.isEmpty()) query += "&";

        List<ShortRecipe> recipes = new ArrayList<>();
        SpoonacularOutput output = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/complexSearch?"
                        + query
                        + "sort=meta-score&sortDirection=desc"
                        + "&number=" + number
                        + "&offset=" + offset
                        + "&apiKey=" + apiKey,
                SpoonacularOutput.class);
        recipes.addAll(output.getResults());
        recipes.forEach(r -> r.add(
                linkTo(methodOn(RecipeController.class).getRecipeById(r.getId())).withRel("Get recipe details")
        ));

        return recipes;
    }
}

