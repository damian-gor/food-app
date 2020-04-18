package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.DiaryPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/profiles/{profileId}/food-diary/pages")
@Api(tags = "6. Profiles.FoodDiary.DiaryPages")
public class DiaryPageController {

    @Autowired
    private DiaryPageService diaryPageService;

    @ApiOperation(value = "Get all profile's diary pages",
            response = DiaryPage.class)
    @GetMapping
    public ResponseEntity<List<DiaryPage>> getAllDiaryPages(@PathVariable Long profileId) {
        return ResponseEntity.ok(diaryPageService.getAllDiaryPages(profileId));
    }

    @ApiOperation(value = "Get profile's diary page by date",
            notes = "Enter the date using the following pattern: YYYY-MM-DD",
            response = DiaryPage.class)
    @GetMapping("/{stringDate}")
    public ResponseEntity<DiaryPage> getDiaryPage(@PathVariable Long profileId,
                                                  @PathVariable String stringDate) {
        return ResponseEntity.ok(diaryPageService.getDiaryPage(profileId, stringDate));
    }

    @ApiOperation(value = "Add profile's diary page",
            notes = "You can enter the date using the following pattern: YYYY-MM-DD. If not, today's date will be set automatically ",
            response = DiaryPage.class)
    @PostMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<DiaryPage> addDiaryPage(@PathVariable Long profileId,
                                                  @RequestParam(required = false, name = "date") String stringDate) {
        return ResponseEntity.ok(diaryPageService.addDiaryPage(profileId, stringDate));
    }

    @ApiOperation(value = "Remove profile's diary page",
            notes = "Enter the date using the following pattern: YYYY-MM-DD",
            response = Message.class)
    @DeleteMapping("/{stringDate}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeDiaryPage(@PathVariable Long profileId,
                                                   @PathVariable String stringDate) {
        return ResponseEntity.ok(diaryPageService.removeDiaryPage(profileId, stringDate));
    }
}
