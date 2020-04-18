package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.*;
import com.damgor.foodapp.service.MealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profiles/{profileId}/food-diary/pages/{stringDate}/meals")
@Api(tags = "7. Profiles.FoodDiary.DiaryPages.Meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @ApiOperation(value = "Get all profile's meals in selected diary page",
            notes = "Enter profileId and the diary page's date using the following pattern: YYYY-MM-DD",
            response = Meal.class)
    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals(@PathVariable Long profileId,
                                                  @PathVariable String stringDate) {
        return ResponseEntity.ok(mealService.getAllMeals(profileId, stringDate));
    }

    @ApiOperation(value = "Get selected meal in selected diary page",
            notes = "Enter profileId, mealId and the diary page's date using the following pattern: YYYY-MM-DD",
            response = Meal.class)
    @GetMapping("/{mealId}")
    public ResponseEntity<Meal> getMeal(@PathVariable Long profileId,
                                        @PathVariable String stringDate,
                                        @PathVariable Integer mealId) {
        return ResponseEntity.ok(mealService.getMeal(profileId, stringDate, mealId));
    }

    @ApiOperation(value = "Add meal in selected diary page",
            notes = "Enter profileId, mealId and the diary page's date using the following pattern: YYYY-MM-DD",
            response = Meal.class)
    @PostMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Meal> addMeal(@PathVariable Long profileId,
                                        @PathVariable String stringDate,
                                        @RequestBody Map<String, Integer> elements) {
        Meal newMeal = new Meal(profileId, Date.valueOf(stringDate));
        newMeal.setElements(elements);
        return ResponseEntity.ok(mealService.addMeal(newMeal));
    }

    @ApiOperation(value = "Overwrite selected meal in selected diary page",
            notes = "Enter profileId, mealId and the diary page's date using the following pattern: YYYY-MM-DD. Request " +
                    "body should contain mapped product ids together with their quantities consumed [grams].",
            response = Meal.class)
    @PutMapping("/{mealId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long profileId,
                                           @PathVariable String stringDate,
                                           @PathVariable Integer mealId,
                                           @RequestBody Map<String, Integer> elements) {
        Meal updatedMeal = new Meal();
        updatedMeal.setId(new MealId(profileId, Date.valueOf(stringDate), mealId));
        updatedMeal.setElements(elements);
        return ResponseEntity.ok(mealService.updateMeal(updatedMeal));
    }

    @ApiOperation(value = "Add more products to the selected meal in selected diary page",
            notes = "Enter profileId, mealId and the diary page's date using the following pattern: YYYY-MM-DD. Request " +
                    "body should contain mapped product ids together with their quantities consumed [grams].",
            response = Meal.class)
    @PatchMapping("/{mealId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Meal> pratialUpdateMeal(@PathVariable Long profileId,
                                                  @PathVariable String stringDate,
                                                  @PathVariable Integer mealId,
                                                  @RequestBody Map<String, Integer> elements) {
        Meal updatedMeal = new Meal();
        updatedMeal.setId(new MealId(profileId, Date.valueOf(stringDate), mealId));
        updatedMeal.setElements(elements);
        return ResponseEntity.ok(mealService.partialUpdateMeal(updatedMeal));
    }

    @ApiOperation(value = "Remove selected meal from selected diary page",
            notes = "Enter profileId, mealId and the diary page's date using the following pattern: YYYY-MM-DD.",
            response = Message.class)
    @DeleteMapping("/{mealId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeMeal (@PathVariable Long profileId,
                                               @PathVariable String stringDate,
                                               @PathVariable Integer mealId) {
        return ResponseEntity.ok(mealService.removeMeal(profileId,stringDate,mealId));
    }
}
































