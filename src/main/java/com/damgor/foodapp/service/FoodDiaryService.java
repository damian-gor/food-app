package com.damgor.foodapp.service;

import com.damgor.foodapp.model.FoodDiary;
import com.damgor.foodapp.model.Message;

public interface FoodDiaryService {
    FoodDiary getFoodDiary (Long profileId);
    FoodDiary addFoodDiary (Long profileId);
    Message removeFoodDiary (Long profileId);
}
