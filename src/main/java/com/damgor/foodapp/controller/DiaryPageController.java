package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.DiaryPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DiaryPage> getDiaryPage(@PathVariable Long profileId,
                                                  @PathVariable String stringDate) {
        return ResponseEntity.ok(diaryPageService.getDiaryPage(profileId, stringDate));
    }

    @PostMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<DiaryPage> addDiaryPage(@PathVariable Long profileId,
                                                  @RequestParam(required = false, name = "date") String stringDate) {
        return ResponseEntity.ok(diaryPageService.addDiaryPage(profileId, stringDate));
    }

    @DeleteMapping("/{stringDate}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeDiaryPage(@PathVariable Long profileId,
                                                   @PathVariable String stringDate) {
        return ResponseEntity.ok(diaryPageService.removeDiaryPage(profileId, stringDate));
    }
}
