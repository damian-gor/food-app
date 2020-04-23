package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.DiaryPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@RequestMapping("/ui/profiles/{profileId}/food-diary/pages")
@ApiIgnore
public class DiaryPageControllerUI {

    @Autowired
    private DiaryPageService diaryPageService;

    @GetMapping
    public ModelAndView getAllDiaryPages(@PathVariable Long profileId) {
        ModelAndView modelAndView = new ModelAndView("diary-pages");
        List<DiaryPage> diaryPages = diaryPageService.getAllDiaryPages(profileId);
        modelAndView.addObject("diaryPages", diaryPages);
        return modelAndView;
    }

    @GetMapping("/{stringDate}")
    public ModelAndView getDiaryPage(@PathVariable Long profileId,
                                     @PathVariable String stringDate) {
        ModelAndView modelAndView = new ModelAndView("diary-page");
        DiaryPage diaryPage = diaryPageService.getDiaryPage(profileId, stringDate);
        modelAndView.addObject("diaryPage", diaryPage);
        return modelAndView;
    }

    @PostMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ModelAndView addDiaryPage(@PathVariable Long profileId,
                                                  @RequestParam(required = false, name = "date") String stringDate) {
        ModelAndView modelAndView = new ModelAndView("diary-page");
        DiaryPage diaryPage = diaryPageService.addDiaryPage(profileId, stringDate);
        modelAndView.addObject("diaryPage", diaryPage);
        return modelAndView;
    }

    @DeleteMapping("/{stringDate}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ModelAndView removeDiaryPage(@PathVariable Long profileId,
                                                   @PathVariable String stringDate) {
        ModelAndView modelAndView = new ModelAndView("message");
        Message message = diaryPageService.removeDiaryPage(profileId, stringDate);
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
