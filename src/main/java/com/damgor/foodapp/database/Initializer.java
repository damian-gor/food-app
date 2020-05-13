package com.damgor.foodapp.database;

import com.damgor.foodapp.model.*;
import com.damgor.foodapp.model.enums.*;
import com.damgor.foodapp.repository.ProfileDetailsRepository;
import com.damgor.foodapp.repository.ProfileRepository;
import com.damgor.foodapp.repository.UserRepository;
import com.damgor.foodapp.service.DiaryPageService;
import com.damgor.foodapp.service.FoodDiaryService;
import com.damgor.foodapp.service.MealService;
import com.damgor.foodapp.service.serviceImpl.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileDetailsRepository profileDetailsRepository;
    @Autowired
    private DiaryPageService diaryPageService;
    @Autowired
    private FoodDiaryService foodDiaryService;
    @Autowired
    private MealService mealService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RecipeServiceImpl recipeService;


    @Override
    public void run(String... args) throws MalformedURLException {

///////////////    PROFILE    ///////////////////

        ArrayList<String> favouriteProductsSample = new ArrayList<>();
        favouriteProductsSample.add("food_ac9n68caswlpggbp7727varlyjk5");
        favouriteProductsSample.add("food_a3049hmbqj5wstaeeb3udaz6uaqv");
        favouriteProductsSample.add("food_ai215e5b85pdh5ajd4aafa3w2zm8");
        favouriteProductsSample.add("food_awz3iefajbk1fwahq9logahmgltj");
        favouriteProductsSample.add("430682");
        favouriteProductsSample.add("209138");

        ArrayList<Integer> favouriteRecipesSample = new ArrayList<>();
        favouriteRecipesSample.add(636178);
        favouriteRecipesSample.add(659135);


        Profile profile1 = new Profile();
        profile1.setId(1L);
        profile1.setName("Damian");
        profile1.setCuisine(Cuisine.Greek);
        profile1.setDiet(Diet.Vegetarian);
        profile1.setIntolerance(Intolerance.Sulfite);
        profile1.setFavouriteProducts(favouriteProductsSample);
        profile1.setFavouriteRecipes(favouriteRecipesSample);

        Profile profile2 = new Profile();
        profile2.setId(2L);
        profile2.setName("Konrad");
        profile2.setCuisine(Cuisine.European);
        profile2.setDiet(Diet.Paleo);
        profile2.setFavouriteRecipes(favouriteRecipesSample);

        Profile profile3 = new Profile();
        profile3.setId(3L);
        profile3.setName("Richard");
        profile3.setCuisine(Cuisine.Greek);
        profile3.setDiet(Diet.Paleo);
        profile3.setIntolerance(Intolerance.Peanut);
        profile3.setFavouriteProducts(favouriteProductsSample);
        profile3.setFavouriteRecipes(favouriteRecipesSample);

        profileRepository.save(profile1);
        profileRepository.save(profile2);
        profileRepository.save(profile3);

        ///////////////    PROFILE DETAILS    ///////////////////

        ProfileDetails details = new ProfileDetails(1L, 185.0, 80.0, 1994, Sex.M, Aim.WEIGHT_GAIN, ActivityLevel.MODERATE_EXERCISE);
        ProfileDetails details3 = new ProfileDetails(3L, 180.0, 85.0, 1961, Sex.M, Aim.KEEP_WEIGHT, ActivityLevel.MODERATE_EXERCISE);
        profileDetailsRepository.save(details);
        profileDetailsRepository.save(details3);

        ///////////////    FOOD DIARY    ///////////////////

        foodDiaryService.addFoodDiary(profile1.getId());
        foodDiaryService.addFoodDiary(profile3.getId());

        ///////////////    DIARY PAGE    ///////////////////

        DiaryPage dp1 = new DiaryPage(profile1.getId());
        DiaryPage dp2 = new DiaryPage(profile1.getId(), new java.sql.Date(120, 3, 11));

        diaryPageService.addDiaryPage(profile1.getId(), null);
        diaryPageService.addDiaryPage(profile1.getId(), "2020-04-05");

        ///////////////    MEAL    ///////////////////

        Map<String, Integer> products1 = new HashMap<>();
        products1.put("food_ay85fsfagbevt9brp5jnra6w3vp7", 50);
        products1.put("101207", 80);
        products1.put("126586", 120);
        products1.put("food_bpumdjzb5rtqaeabb0kbgbcgr4t9", 165);

        Map<String, Integer> products2 = new HashMap<>();
        products2.put("food_beu36e7ao1sz5obzslxr5bu8tg9u", 350);
        products2.put("101307", 70);
        products2.put("126586", 14);
        products2.put("food_bhppgmha1u27voagb8eptbp9g376", 165);
        products2.put("food_aoceuc6bshdej1bbsdammbnj6l6o", 120);

        Map<String, Integer> products3 = new HashMap<>();
        products3.put("food_ai215e5b85pdh5ajd4aafa3w2zm8", 250);
        products3.put("101347", 170);
        products3.put("126536", 124);
        products3.put("food_a6k79rrahp8fe2b26zussa3wtkqh", 465);

        Map<String, Integer> products4 = new HashMap<>();
        products4.put("food_ai215e5b85pdh5ajd4aafa3w2zm8", 250);
        products4.put("food_a6k79rrahp8fe2b26zussa3wtkqh", 465);
        products4.put("101349", 170);


        Meal m1 = new Meal(profile1.getId(), null, 1, products1);
        Meal m2 = new Meal(profile1.getId(), null, 2, products2);
        Meal m3 = new Meal(profile1.getId(), null, 3, products3);
        Meal m4 = new Meal(profile1.getId(), Date.valueOf("2020-04-13"), 1, products4);

        mealService.addMeal(m1);
        mealService.addMeal(m2);
        mealService.addMeal(m3);
        mealService.addMeal(m4);

        ///////////////    USERS    ///////////////////

        userRepository.save(new User(99999L, "admin", passwordEncoder.encode("pass"), true, "ROLE_ADMIN,ROLE_USER"));
        userRepository.save(new User(profile1.getId(), "user1", passwordEncoder.encode("pass"), true, "ROLE_USER"));
        userRepository.save(new User(profile2.getId(), "user2", passwordEncoder.encode("pass"), true, "ROLE_USER"));
        userRepository.save(new User(profile3.getId(), "user3", passwordEncoder.encode("pass"), true, "ROLE_USER"));
        userRepository.save(new User(4L, "user4", passwordEncoder.encode("pass"), false, "ROLE_USER"));

        ///////////////    RECIPES    ///////////////////

        recipeService.addRecipesToCache(new Recipe(
                636178,
                "Broccoli Cheddar Soup, A Panera Bread Co. Copycat",
                11,
                77.0,
                25.0,
                45,
                4,
                1.96,
                Arrays.asList("lacto ovo vegetarian"),
                Arrays.asList("bread"),
                new URL("https://spoonacular.com/broccoli-cheddar-soup-a-panera-bread-co-copycat-636178"),
                Arrays.asList(
                        new Ingredients("bay leaves", "2 bay leaves", 2.0, ""),
                        new Ingredients("broccoli florets", "4 cups broccoli florets (about 1 head)", 4.0, "cups")),
                "Melt the butter in a large Dutch oven or pot over medium heat. Add the onion and cook until tender, about 5 minutes. Whisk in the flour and cook until golden, 3 to 4 minutes, then gradually whisk in the half-and-half until smooth.Add the chicken broth, bay leaves and nutmeg, then season with salt and pepper and bring to a simmer.Reduce the heat to medium-low and cook, uncovered, until thickened, about 20 minutes.Meanwhile, prepare the bread bowls: Using a sharp knife, cut a circle into the top of each loaf, leaving a 1-inch border all around.Remove the bread top, then hollow out the middle with a fork or your fingers, leaving a thick bread shell.Add the broccoli and carrot to the broth mixture and simmer until tender, about 20 minutes.Discard the bay leaves.Puree the soup in batches in a blender until smooth; you'll still have flecks of carrot and broccoli.Return to the pot. (Or puree the soup in the pot with an immersion blender.)Add the cheese to the soup and whisk over medium heat until melted.Add up to 3/4 cup water if the soup is too thick.Ladle into the bread bowls and garnish with cheese."
        ));
        recipeService.addRecipesToCache(new Recipe(
                        659135,
                        "Salmon with roasted vegetables",
                        7,
                        94.0,
                        100.0,
                        45,
                        2,
                        5.16,
                        Arrays.asList("gluten free", "dairy free", "pescatarian"),
                        Arrays.asList("gluten free", "dairy free", "pescatarian"),
                        new URL("https://spoonacular.com/salmon-with-roasted-vegetables-659135"),
                        Arrays.asList(
                                new Ingredients("potato", "1 potato", 1.0, ""),
                                new Ingredients("parsnip", "1 parsnip", 1.0, ""),
                                new Ingredients("onion", "1 onion, sliced", 1.0, ""),
                                new Ingredients("cherry tomatoes", "150 g cherry tomatoes", 150.0, "g")),
                        "Season the salmon fillets with some salt, pepper and a pinch of paprika and keep aside. Preheat the oven to 200 C.Roughly dice the potatoes, parsnips and carrots and add to a roasting tray. Drizzle over the olive oil and season with salt and pepper. Mix well and roast for 15 minutes. Add in the onion and roast for a further 10-15 minutes Place the salmon fillets and tomatoes between the veg. Drizzle the lemon juice and sprinkle over the rosemary and thyme. Season lightly with salt and pepper and roast for 10-15 minutes or until the salmon and veg is cooked through. Serve with some green salad."
                )
        );
    }

}
