package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.FoodDiary;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.FoodDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/ui/profiles/{profileId}/food-diary")
@ApiIgnore
public class FoodDiaryControllerUI {

    @Autowired
    private FoodDiaryService foodDiaryService;

    @GetMapping
    public ModelAndView getFoodDiary(@PathVariable Long profileId) {
        ModelAndView modelAndView = new ModelAndView("food-diary");
        FoodDiary foodDiary = foodDiaryService.getFoodDiary(profileId);
        modelAndView.addObject("foodDiary", foodDiary);
        return modelAndView;
    }

    @PostMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ModelAndView addFoodDiary(@PathVariable Long profileId) {
        ModelAndView modelAndView = new ModelAndView("food-diary");
        FoodDiary foodDiary = foodDiaryService.addFoodDiary(profileId);
        modelAndView.addObject("foodDiary", foodDiary);
        return modelAndView;
    }

    @DeleteMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ModelAndView removeFoodDiary(@PathVariable Long profileId) {
        ModelAndView modelAndView = new ModelAndView("message");
        Message message = foodDiaryService.removeFoodDiary(profileId);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

}
