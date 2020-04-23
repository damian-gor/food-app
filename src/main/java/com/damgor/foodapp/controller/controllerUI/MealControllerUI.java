package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.Meal;
import com.damgor.foodapp.model.MealId;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.service.DiaryPageService;
import com.damgor.foodapp.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ui/profiles/{profileId}/food-diary/pages/{stringDate}/meals")
@ApiIgnore
public class MealControllerUI {

    @Autowired
    private MealService mealService;
    @Autowired
    private DiaryPageService DiaryPageService;

    @GetMapping
    public ModelAndView getAllMeals(@PathVariable Long profileId,
                                                  @PathVariable String stringDate) {
        ModelAndView modelAndView = new ModelAndView("meals");
        List<Meal> meals = mealService.getAllMeals(profileId, stringDate);
        DiaryPage diaryPage = DiaryPageService.getDiaryPage(profileId,stringDate);
        modelAndView.addObject("meals", meals);
        modelAndView.addObject("diaryPage", diaryPage);
        return modelAndView;
    }

    @GetMapping("/{mealId}")
    public ModelAndView getMeal(@PathVariable Long profileId,
                                        @PathVariable String stringDate,
                                        @PathVariable Integer mealId) {
        ModelAndView modelAndView = new ModelAndView("meal");
        Meal meal = mealService.getMeal(profileId, stringDate, mealId);
        modelAndView.addObject("meal", meal);
        return modelAndView;
    }

    @PostMapping
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Meal> addMeal(@PathVariable Long profileId,
                                        @PathVariable String stringDate,
                                        @RequestBody Map<String, Integer> elements) {
        Meal newMeal = new Meal(profileId, Date.valueOf(stringDate));
        newMeal.setElements(elements);
        return ResponseEntity.ok(mealService.addMeal(newMeal));
    }

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

    @DeleteMapping("/{mealId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeMeal (@PathVariable Long profileId,
                                               @PathVariable String stringDate,
                                               @PathVariable Integer mealId) {
        return ResponseEntity.ok(mealService.removeMeal(profileId,stringDate,mealId));
    }
}
































