package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Meal;
import com.damgor.foodapp.model.MealId;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profiles/{profileId}/food-diary/pages/{stringDate}/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals(@PathVariable Long profileId,
                                                  @PathVariable String stringDate) {
        return ResponseEntity.ok(mealService.getAllMeals(profileId, stringDate));
    }

    @GetMapping("/{mealId}")
    public ResponseEntity<Meal> getMeal(@PathVariable Long profileId,
                                        @PathVariable String stringDate,
                                        @PathVariable Integer mealId) {
        return ResponseEntity.ok(mealService.getMeal(profileId, stringDate, mealId));
    }

    //    In request body required only map: productId - grams.
    @PostMapping
    public ResponseEntity<Meal> addMeal(@PathVariable Long profileId,
                                        @PathVariable String stringDate,
                                        @RequestBody Map<String, Integer> elements) {
        Meal newMeal = new Meal(profileId, Date.valueOf(stringDate));
        newMeal.setElements(elements);
        return ResponseEntity.ok(mealService.addMeal(newMeal));
    }

    @PutMapping("/{mealId}")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long profileId,
                                           @PathVariable String stringDate,
                                           @PathVariable Integer mealId,
                                           @RequestBody Map<String, Integer> elements) {
        Meal updatedMeal = new Meal();
        updatedMeal.setId(new MealId(profileId, Date.valueOf(stringDate), mealId));
        updatedMeal.setElements(elements);
        return ResponseEntity.ok(mealService.updateMeal(updatedMeal));
    }

    @PatchMapping("/{mealId}")
    public ResponseEntity<Meal> pratialUpdateMeal(@PathVariable Long profileId,
                                                  @PathVariable String stringDate,
                                                  @PathVariable Integer mealId,
                                                  @RequestBody Map<String, Integer> elements) {
        Meal updatedMeal = new Meal();
        updatedMeal.setId(new MealId(profileId, Date.valueOf(stringDate), mealId));
        updatedMeal.setElements(elements);
        return ResponseEntity.ok(mealService.partialUpdateMeal(updatedMeal));
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Message> removeMeal (@PathVariable Long profileId,
                                               @PathVariable String stringDate,
                                               @PathVariable Integer mealId) {
        return ResponseEntity.ok(mealService.removeMeal(profileId,stringDate,mealId));
    }


}
































