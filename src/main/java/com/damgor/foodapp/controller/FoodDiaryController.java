package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.FoodDiary;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.FoodDiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/profiles/{profileId}/food-diary")
@Api(tags = "5. Profiles.FoodDiary", description = "Each profile can have a food diary. It consists of diary pages, corresponding" +
        " to individual days, which in turn consist of meals. The diary owner can add meals by selecting products from the " +
        "Product module and specifying their quantity. The application will calculate the calories consumed during the day" +
        " and compare them with the caloric demand from the recommendation engine. Information on consumed macronutrients " +
        "(carbs, proteins, fats) will also be available.")
public class FoodDiaryController {

    @Autowired
    private FoodDiaryService foodDiaryService;

    @ApiOperation(value = "Get profile's food diary",
            response = FoodDiary.class)
    @GetMapping
    public ResponseEntity<FoodDiary> getFoodDiary(@PathVariable Long profileId) {
        return ResponseEntity.ok(foodDiaryService.getFoodDiary(profileId));
    }

    @ApiOperation(value = "Add profile's food diary",
            response = FoodDiary.class)
    @PostMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<FoodDiary> addFoodDiary(@PathVariable Long profileId) {
        return ResponseEntity.ok(foodDiaryService.addFoodDiary(profileId));
    }

    @ApiOperation(value = "Remove profile's food diary",
            response = Message.class)
    @DeleteMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeFoodDiary(@PathVariable Long profileId) {
        return ResponseEntity.ok(foodDiaryService.removeFoodDiary(profileId));
    }

}
