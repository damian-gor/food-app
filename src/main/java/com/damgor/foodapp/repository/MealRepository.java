package com.damgor.foodapp.repository;

import com.damgor.foodapp.model.Meal;
import com.damgor.foodapp.model.MealId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface MealRepository extends JpaRepository <Meal, MealId> {
    List<Meal> findByIdProfileIdAndIdDate(Long profileId, Date date);

}
