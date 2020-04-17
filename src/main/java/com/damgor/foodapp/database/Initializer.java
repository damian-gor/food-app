package com.damgor.foodapp.database;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.Meal;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.model.ProfileDetails;
import com.damgor.foodapp.model.enums.*;
import com.damgor.foodapp.repository.ProfileDetailsRepository;
import com.damgor.foodapp.repository.ProfileRepository;
import com.damgor.foodapp.model.User;
import com.damgor.foodapp.repository.UserRepository;
import com.damgor.foodapp.service.DiaryPageService;
import com.damgor.foodapp.service.FoodDiaryService;
import com.damgor.foodapp.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
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

    @Override
    public void run(String... args) {

///////////////    PROFILE    ///////////////////

        ArrayList<String> favouritesSample1 = new ArrayList<>();
        favouritesSample1.add("hard-coded-product1");
        favouritesSample1.add("hard-coded-product2");

        Profile profile1 = new Profile();
        profile1.setId(1L);
        profile1.setName("Damian");
        profile1.setCuisine(Cuisine.Greek);
        profile1.setDiet(Diet.Vegetarian);
        profile1.setIntolerance(Intolerance.Sulfite);
        profile1.setFavouriteProducts(favouritesSample1);

        ArrayList<Integer> favouritesSample2 = new ArrayList<>();
        favouritesSample2.add(1234);
        favouritesSample2.add(4567);

        Profile profile2 = new Profile();
        profile2.setId(2L);
        profile2.setName("Konrad");
        profile2.setCuisine(Cuisine.European);
        profile2.setDiet(Diet.Paleo);
        profile2.setFavouriteRecipes(favouritesSample2);

        Profile profile3 = new Profile();
        profile3.setId(3L);
        profile3.setName("Richard");
        profile3.setCuisine(Cuisine.Greek);
        profile3.setDiet(Diet.Paleo);
        profile3.setIntolerance(Intolerance.Peanut);
        profile3.setFavouriteProducts(favouritesSample1);
        profile3.setFavouriteRecipes(favouritesSample2);

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
        Meal m2 = new Meal(profile1.getId(), null,2, products2);
        Meal m3 = new Meal(profile1.getId(), null, 3, products3);
        Meal m4 = new Meal(profile1.getId(), Date.valueOf("2020-04-13"),1, products4);


        mealService.addMeal(m1);
        mealService.addMeal(m2);
        mealService.addMeal(m3);
        mealService.addMeal(m4);

        ///////////////    USERS    ///////////////////

        userRepository.save(new User(99999L,"admin", passwordEncoder.encode("pass"), true, "ROLE_ADMIN,ROLE_USER"));
        userRepository.save(new User(profile1.getId(),"user1", passwordEncoder.encode("pass"), true, "ROLE_USER"));
        userRepository.save(new User(profile2.getId(),"user2", passwordEncoder.encode("pass"), true, "ROLE_USER"));
        userRepository.save(new User(profile3.getId(),"user3", passwordEncoder.encode("pass"), true, "ROLE_USER"));
        userRepository.save(new User(4L,"user4", passwordEncoder.encode("pass"), false, "ROLE_USER"));


    }

}
