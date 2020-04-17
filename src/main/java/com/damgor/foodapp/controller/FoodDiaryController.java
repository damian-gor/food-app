package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.FoodDiary;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.FoodDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/profiles/{profileId}/food-diary")
public class FoodDiaryController {

    @Autowired
    private FoodDiaryService foodDiaryService;

    @GetMapping
    public ResponseEntity<FoodDiary> getFoodDiary(@PathVariable Long profileId) {
        return ResponseEntity.ok(foodDiaryService.getFoodDiary(profileId));
    }

    @PostMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<FoodDiary> addFoodDiary(@PathVariable Long profileId) {
        return ResponseEntity.ok(foodDiaryService.addFoodDiary(profileId));
    }

    @DeleteMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeFoodDiary(@PathVariable Long profileId) {
        return ResponseEntity.ok(foodDiaryService.removeFoodDiary(profileId));
    }

}