package com.damgor.foodapp.database;

import com.damgor.foodapp.model.*;
import com.damgor.foodapp.model.enums.*;
import com.damgor.foodapp.repository.ProfileDetailsRepository;
import com.damgor.foodapp.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileDetailsRepository profileDetailsRepository;

    @Override
    public void run(String... args) throws Exception {

        ArrayList <String> list1 = new ArrayList<>();
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



        ArrayList <Integer> list = new ArrayList<>();
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

        ProfileDetails php = new ProfileDetails(1L,185.0, 80.0, 1994, Sex.M, Aim.WEIGHT_GAIN, ActivityLevel.MODERATE_EXERCISE);
        profileDetailsRepository.save(php);
    }

}
