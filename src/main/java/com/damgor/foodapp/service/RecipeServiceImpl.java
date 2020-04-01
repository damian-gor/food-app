package com.damgor.foodapp.service;

import com.damgor.foodapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

//    wyrzucic exception dla wszystkich: {
//    "status": "failure",
//    "code": 402,
//    "message": "Your daily points limit of 150 has been reached. Please upgrade your plan to continue using the API."
//} status: 402 Payment Required


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ProfileService profileService;

    @Value("${spoonacular.apiKey}")
    private String apiKey;


    @Override
    public Recipe getRandomRecipe() {
        SpoonacularOutput spoonacularOutput = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/random?"
                        + "apiKey=" + apiKey,
                SpoonacularOutput.class);
        Recipe recipe = spoonacularOutput.getRecipes().get(0);
        return recipe;
    }

    @Override
    public List<ShortRecipe> getRecipesByIngredients(String ingredients) {
        ShortRecipe[] spoonacularOutput = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/findByIngredients?"
                        + "ranking=1"
                        + "&ingredients=" + ingredients
                        + "&apiKey=" + apiKey,
                ShortRecipe[].class);
        List<ShortRecipe> recipes = Arrays.asList(spoonacularOutput);
        return recipes;
    }

    //    Potem ogarnać error handling dla 404
    @Override
    public Recipe getRecipeById(int id) {
        Recipe recipe;
        try {
            recipe = restTemplate.getForObject(
                    "https://api.spoonacular.com/recipes/"
                            + id
                            + "/information?"
                            + "apiKey=" + apiKey,
                    Recipe.class);
            return recipe;
        } catch (HttpClientErrorException.NotFound e) {
            recipe = new Recipe();
            return recipe;
        }
    }

    @Override
    public List<ShortRecipe> getRecipesByText(String text) {
        String query = text.replaceAll(" ", "+");
        List<ShortRecipe> recipes = new ArrayList<>();
        SpoonacularSearchOutput spoonacularSearchOutput = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/search"
                        + "?query=" + query
                        + "&apiKey=" + apiKey,
                SpoonacularSearchOutput.class);
        recipes.addAll(spoonacularSearchOutput.getResults());
        return recipes;
    }

    @Override
    public List<ShortRecipe> getMostSuitableRecipes(long profileId) {
        Profile profile = profileService.getProfile(profileId);
        String query = "";

        if (profile.getCuisine() != null) query += "cuisine=" + profile.getCuisine();
        if (profile.getDiet() != null) query += "&diet=" + profile.getDiet();
        if (profile.getIntolerance() != null) query += "&intolerances=" + profile.getIntolerance();
        if (query.isEmpty() == true) query = "&";

        List<ShortRecipe> recipes = new ArrayList<>();
        SpoonacularSearchOutput spoonacularSearchOutput = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/complexSearch?"
                        + query
                        + "sort=meta-score&sortDirection=desc&number=5"
                        + "&apiKey=" + apiKey,
                SpoonacularSearchOutput.class);
        recipes.addAll(spoonacularSearchOutput.getResults());

        return recipes;
    }
}

