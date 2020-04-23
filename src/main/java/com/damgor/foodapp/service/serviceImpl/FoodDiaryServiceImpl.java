package com.damgor.foodapp.service.serviceImpl;

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

@Service
public class FoodDiaryServiceImpl implements FoodDiaryService {

    @Autowired
    private FoodDiaryRepository foodDiaryRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileDetailsService profileDetailsService;
    @Autowired
    private LinkProvider linkProvider;

    @Override
    public FoodDiary getFoodDiary(Long profileId) {
        FoodDiary foodDiary = getFoodDiaryIfExist(profileId);
        actualizeCaloricIntakeGoal(foodDiary);
        foodDiaryRepository.save(foodDiary);
        linkProvider.addFoodDiaryLinks(foodDiary);
        return foodDiary;
    }

    @Override
    public Message removeFoodDiary(Long profileId) {
        getFoodDiaryIfExist(profileId);
        foodDiaryRepository.deleteById(profileId);
        Message message = new Message("Your Diary has been removed successfully.");
        linkProvider.addFoodDiaryMessageLinks(message,profileId);
        return message;
    }

    @Override
    public FoodDiary addFoodDiary(Long profileId) {
        profileService.getProfile(profileId);
        FoodDiary foodDiary = new FoodDiary(profileId);
        actualizeCaloricIntakeGoal(foodDiary);
        foodDiaryRepository.save(foodDiary);
        linkProvider.addFoodDiaryLinks(foodDiary);
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

    private void actualizeCaloricIntakeGoal(FoodDiary foodDiary) {
        Double caloricIntakeGoal = profileDetailsService.getProfileDetails(foodDiary.getProfileId()).getRecommendedCaloricIntake();
        if (caloricIntakeGoal == null) caloricIntakeGoal = 0.0;
        foodDiary.setCaloricIntakeGoal(caloricIntakeGoal);
    }

}