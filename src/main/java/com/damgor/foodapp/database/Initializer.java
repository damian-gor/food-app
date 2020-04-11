package com.damgor.foodapp.database;

import com.damgor.foodapp.model.*;
import com.damgor.foodapp.model.enums.*;
import com.damgor.foodapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.*;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileDetailsRepository profileDetailsRepository;

    @Autowired
    private FoodDiaryRepository foodDiaryRepository;
    @Autowired
    private DiaryPageRepository diaryPageRepository;
    @Autowired
    private MealRepository mealRepository;

    @Override
    public void run(String... args) throws Exception {

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("1234afwfa");
        list1.add("aaaaa");

        Profile profile1 = new Profile();
        profile1.setId(1L);
        profile1.setName("Damian");
        profile1.setCuisine(Cuisine.Greek);
        profile1.setDiet(Diet.Vegetarian);
        profile1.setIntolerance(Intolerance.Sulfite);
        profile1.setFavouriteProducts(list1);
//        profile1.setProfileDetails(185.0, 80.0, 26, Sex.M, Aim.WEIGHT_GAIN, ActivityLevel.MODERATE_EXERCISE);


        ArrayList<Integer> list = new ArrayList<>();
        list.add(1234);
        list.add(4567);

        Profile profile2 = new Profile();
        profile2.setId(2L);
        profile2.setName("Konrad");
        profile2.setCuisine(Cuisine.European);
        profile2.setDiet(Diet.Paleo);
        profile2.setFavouriteRecipes(list);

        Profile profile3 = new Profile();
        profile3.setId(3L);
        profile3.setName("Richard");
        profile3.setCuisine(Cuisine.Greek);
        profile3.setDiet(Diet.Paleo);
        profile3.setIntolerance(Intolerance.Peanut);
        profile1.setFavouriteProducts(list1);
        profile3.setFavouriteRecipes(list);

        profileRepository.save(profile1);
        profileRepository.save(profile2);
        profileRepository.save(profile3);

        ProfileDetails details = new ProfileDetails(1L, 185.0, 80.0, 1994, Sex.M, Aim.WEIGHT_GAIN, ActivityLevel.MODERATE_EXERCISE);
        ProfileDetails details3 = new ProfileDetails(3L, 180.0, 85.0, 1961, Sex.M, Aim.KEEP_WEIGHT, ActivityLevel.MODERATE_EXERCISE);
        profileDetailsRepository.save(details);
        profileDetailsRepository.save(details3);

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Map <Integer,Integer> map = new HashMap<>();
        map.put(3,4);
        map.put(5,6);
        map.put(11,9);

        ProfileDetails profile1Details = profileDetailsRepository.findById(profile1.getId()).get();
//        ProfileDetails profile1Details3 = profileDetailsRepository.findById(profile3.getId()).get();

        FoodDiary fd = new FoodDiary(profile1.getId(), details.getRecommendedCaloricIntake());
        FoodDiary fd3 = new FoodDiary(profile3.getId(), details3.getRecommendedCaloricIntake());
        DiaryPage dp1 = new DiaryPage(
                profile1.getId(),
                profile1Details.getRecommendedCaloricIntake(),
                500,
                400,
                200.0,
                300.0,
                100.0);
        DiaryPage dp2 = new DiaryPage(
                profile1.getId(),
                formatter.parse("2020-04-06"),
                profile1Details.getRecommendedCaloricIntake()+1000,
                1500,
                1400,
                1200.0,
                1300.0,
                1100.0);

        Meal m1 = new Meal(dp1,1, map, 10.0, 15.0, 20.0, 30.0);
        Meal m2 = new Meal(dp1,2, map, 210.0, 215.0, 220.0, 230.0);
        Meal m3 = new Meal(dp1,3, map, 310.0, 315.0, 320.0, 330.0);
        Meal m4 = new Meal(dp2,1, map, 410.0, 415.0, 420.0, 430.0);
        Meal m5 = new Meal(dp2,2, map, 510.0, 515.0, 520.0, 530.0);

        foodDiaryRepository.save(fd);
        foodDiaryRepository.save(fd3);
        diaryPageRepository.save(dp1);
        diaryPageRepository.save(dp2);
        mealRepository.save(m1);
        mealRepository.save(m2);
        mealRepository.save(m3);
        mealRepository.save(m4);
        mealRepository.save(m5);
    }

}
