package com.damgor.foodapp.database;

import com.damgor.foodapp.model.Cuisine;
import com.damgor.foodapp.model.Diet;
import com.damgor.foodapp.model.Intolerance;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Initializer implements CommandLineRunner {


    @Autowired
    ProfileRepository profileRepository;


    @Override
    public void run(String... args) throws Exception {
        Profile profile1 = new Profile();
        profile1.setId(1);
        profile1.setName("Damian");
        profile1.setCuisine(Cuisine.Greek);
        profile1.setDiet(Diet.Vegetarian);
        profile1.setIntolerance(Intolerance.Sulfite);


        ArrayList <Integer> list = new ArrayList<>();
        list.add(1234);
        list.add(4567);

        Profile profile2 = new Profile();
        profile2.setId(2);
        profile2.setName("Konrad");
        profile2.setCuisine(Cuisine.European);
        profile2.setDiet(Diet.Paleo);
        profile2.setFavouriteRecipes(list);

        profileRepository.save(profile1);
        profileRepository.save(profile2);

    }


}
