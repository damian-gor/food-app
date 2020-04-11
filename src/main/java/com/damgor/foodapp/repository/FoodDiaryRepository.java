package com.damgor.foodapp.repository;

import com.damgor.foodapp.model.FoodDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodDiaryRepository extends JpaRepository <FoodDiary, Long> {
}
