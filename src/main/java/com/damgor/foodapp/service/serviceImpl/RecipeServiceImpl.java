package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.model.ShortRecipe;
import com.damgor.foodapp.model.SpoonacularOutput;
import com.damgor.foodapp.service.ProfileService;
import com.damgor.foodapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"cacheRecipes"})
public class RecipeServiceImpl implements RecipeService {

    private List<Recipe> cacheRecipes = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private LinkProvider linkProvider;

    @Value("${spoonacular.apiKey}")
    private String apiKey;

    @Override
    public Recipe getRandomRecipe(long userId) {
        SpoonacularOutput output = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/random?"
                        + "apiKey=" + apiKey,
                SpoonacularOutput.class);
        Recipe recipe = output.getRecipes().get(0);

        linkProvider.addRecipesLinks(recipe, userId);
        return recipe;
    }

    @Override
    public List<ShortRecipe> getRecipesByIngredients(String ingredients, long userId, int number) {
        ShortRecipe[] output = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/findByIngredients?"
                        + "ranking=1"
                        + "&ingredients=" + ingredients
                        + "&number=" + number
                        + "&apiKey=" + apiKey,
                ShortRecipe[].class);
        List<ShortRecipe> recipes = Arrays.asList(output);
        linkProvider.addRecipesLinks(recipes, userId);
        return recipes;
    }

    @Override
    @Cacheable
    public Recipe getRecipeById(int id, long userId) {
        try {
            Recipe recipe = restTemplate.getForObject(
                    "https://api.spoonacular.com/recipes/"
                            + id
                            + "/information?"
                            + "apiKey=" + apiKey,
                    Recipe.class);
            linkProvider.addRecipesLinks(recipe, userId);
            return recipe;
        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException(Recipe.class, "id", String.valueOf(id));
        }
    }

    @Override
    public List<ShortRecipe> getRecipesByText(String text, long userId, int number, int offset) {
        String query = text.replaceAll(" ", "+");
        List<ShortRecipe> recipes = new ArrayList<>();
        SpoonacularOutput output = restTemplate.getForObject(
                "https://api.spoonacular.com/recipes/search"
                        + "?query=" + query
                        + "&number=" + number
                        + "&offset=" + offset
                        + "&apiKey=" + apiKey,
                SpoonacularOutput.class);
        recipes.addAll(output.getResults());
        if (!recipes.isEmpty()) {
            linkProvider.addRecipesLinks(recipes, userId);
            return recipes;
        }
        else throw new EntityNotFoundException(ShortRecipe.class, "query", text);
    }

    @Override
    public List<ShortRecipe> getMostSuitableRecipes(long profileId, int number, int offset, long userId) {
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
        linkProvider.addRecipesLinks(recipes,userId);

        return recipes;
    }

    @Override
    public List<ShortRecipe> getCompromiseRecipes(String profileIds, int number, int offset, long userId) {
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
        linkProvider.addRecipesLinks(recipes,userId);
        return recipes;
    }


    // adding sample recipes manually (in Initializer class) so as not to exceed the external server limit (low budget production $$$)
    public void addRecipesToCache (Recipe recipe){
        cacheRecipes.add(recipe);
    }
}

