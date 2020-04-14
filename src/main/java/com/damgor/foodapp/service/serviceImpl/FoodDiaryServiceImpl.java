package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.DiaryPageController;
import com.damgor.foodapp.controller.FoodDiaryController;
import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.FoodDiary;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.repository.FoodDiaryRepository;
import com.damgor.foodapp.service.FoodDiaryService;
import com.damgor.foodapp.service.ProfileDetailsService;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FoodDiaryServiceImpl implements FoodDiaryService {

    @Autowired
    private FoodDiaryRepository foodDiaryRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileDetailsService profileDetailsService;

    @Override
    public FoodDiary getFoodDiary(Long profileId) {
        FoodDiary foodDiary = getFoodDiaryIfExist(profileId);
        actualizeCaloricIntakeGoal(foodDiary);
        foodDiaryRepository.save(foodDiary);
        addBasicLinks(foodDiary);
        return foodDiary;
    }

    @Override
    public Message removeFoodDiary(Long profileId) {
        getFoodDiaryIfExist(profileId);
        foodDiaryRepository.deleteById(profileId);
        Message message = new Message();
        message.add(linkTo(methodOn(ProfileController.class).getProfile(profileId)).withRel("Back to profile."));
        message.setMessage("Your Diary has been removed successfully. To get back to profile click on the following link.");
        return message;
    }

    @Override
    public FoodDiary addFoodDiary(Long profileId) {
        profileService.getProfile(profileId);
        FoodDiary foodDiary = new FoodDiary(profileId);
        actualizeCaloricIntakeGoal(foodDiary);
        foodDiaryRepository.save(foodDiary);
        addBasicLinks(foodDiary);
        return foodDiary;
    }


/////////////// SUPPORTING METHODS /////////////

    private FoodDiary getFoodDiaryIfExist(Long profileId) {
        profileService.getProfile(profileId);
        Optional<FoodDiary> optionalFoodDiary = foodDiaryRepository.findById(profileId);
        if (optionalFoodDiary.isPresent()) {
            FoodDiary foodDiary = optionalFoodDiary.get();
            return foodDiary;
        } else {
            throw new EntityNotFoundException(FoodDiary.class, "id", profileId.toString());
        }
    }

    private void actualizeCaloricIntakeGoal (FoodDiary foodDiary) {
        Double caloricIntakeGoal = profileDetailsService.getProfileDetails(foodDiary.getProfileId()).getRecommendedCaloricIntake();
        if (caloricIntakeGoal==null) caloricIntakeGoal = 0.0;
        foodDiary.setCaloricIntakeGoal(caloricIntakeGoal);
    }

    private void addBasicLinks(FoodDiary foodDiary) {
        foodDiary.add(
                linkTo(methodOn(FoodDiaryController.class).getFoodDiary(foodDiary.getProfileId())).withSelfRel(),
                linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(foodDiary.getProfileId())).withRel("Get all profile's diary pages"),
                linkTo(methodOn(ProfileController.class).getProfile(foodDiary.getProfileId())).withRel("Back to profile.")
        );
    }
}


























