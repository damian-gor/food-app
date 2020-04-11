package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.DiaryPageId;
import com.damgor.foodapp.model.FoodDiary;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.DiaryPageService;
import com.damgor.foodapp.service.FoodDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/profiles/{profileId}/food-diary/pages")
public class DiaryPageController {

    @Autowired
    private DiaryPageService diaryPageService;

    @GetMapping
    public ResponseEntity<List<DiaryPage>> getAllDiaryPages(@PathVariable Long profileId) {
        return ResponseEntity.ok(diaryPageService.getAllDiaryPages(profileId));
    }

    @GetMapping("/{stringDate}")
    public ResponseEntity<DiaryPage> getDiaryPage(@PathVariable Long profileId, @PathVariable String stringDate) throws ParseException {
        return ResponseEntity.ok(diaryPageService.getDiaryPage(profileId, stringDate));
    }

    @PostMapping
    public ResponseEntity<DiaryPage> addDiaryPage(@PathVariable Long profileId,
                                                  @RequestParam(defaultValue = "1900-01-01") String stringDate) {

        return ResponseEntity.ok(diaryPageService.addDiaryPage(profileId, stringDate));
    }
//
//    @PatchMapping
//    public ResponseEntity<FoodDiary> updateCaloricIntakeGoal(@PathVariable Long profileId,
//                                                             @RequestBody(required = false) FoodDiary foodDiary) {
//        int newCaloricIntakeGoal = 0;
//        if(foodDiary.getCaloricIntakeGoal()!=null) newCaloricIntakeGoal = foodDiary.getCaloricIntakeGoal();
//        return ResponseEntity.ok(foodDiaryService.updateCaloricIntakeGoal(profileId,newCaloricIntakeGoal));
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Message> removeFoodDiary(@PathVariable Long profileId) {
//        return ResponseEntity.ok(foodDiaryService.removeFoodDiary(profileId));
//    }

}
