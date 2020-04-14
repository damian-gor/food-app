package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.DiaryPageController;
import com.damgor.foodapp.controller.MealController;
import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.DiaryPageId;
import com.damgor.foodapp.model.Meal;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.repository.DiaryPageRepository;
import com.damgor.foodapp.service.DiaryPageService;
import com.damgor.foodapp.service.FoodDiaryService;
import com.damgor.foodapp.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DiaryPageServiceImpl implements DiaryPageService {

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @Autowired
    private FoodDiaryService foodDiaryService;

    @Autowired
    private MealService mealService;

    @Override
    public List<DiaryPage> getAllDiaryPages(Long profileId) {
        foodDiaryService.getFoodDiary(profileId);
        List<DiaryPage> pages = diaryPageRepository.findByIdProfileId(profileId);
        pages.forEach(p -> {
            actualizeNutrientsIntake(p);
            actualizeCaloricIntakeGoal(p);
            diaryPageRepository.save(p);
        });
        addBasicLinks(pages);
        return pages;
    }

    @Override
    public DiaryPage getDiaryPage(Long profileId, String stringDate) {
        DiaryPage diaryPage = getDiaryPageIfExist(profileId, Date.valueOf(stringDate));
        actualizeNutrientsIntake(diaryPage);
        actualizeCaloricIntakeGoal(diaryPage);
        diaryPageRepository.save(diaryPage);
        addBasicLinks(diaryPage);
        return diaryPage;
    }

    @Override
    public DiaryPage addDiaryPage(Long profileId, String stringDate) {
        DiaryPage diaryPage = new DiaryPage();

        if (stringDate == null) {
            Date currentDate = new Date(System.currentTimeMillis());
            diaryPage.setId(new DiaryPageId(profileId, currentDate));
        } else {
            diaryPage.setId(new DiaryPageId(profileId, Date.valueOf(stringDate)));
        }
        actualizeCaloricIntakeGoal(diaryPage);
        diaryPageRepository.save(diaryPage);

        diaryPage = getDiaryPageIfExist(profileId, diaryPage.getId().getDate());
        addBasicLinks(diaryPage);
        return diaryPage;
    }

    @Override
    public Message removeDiaryPage(Long profileId, String stringDate) {
        Date date = Date.valueOf(stringDate);
        getDiaryPageIfExist(profileId, date);

        diaryPageRepository.deleteById(new DiaryPageId(profileId, date));

        Message message = new Message();
        message.add(linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(profileId)).withRel("Get all profile's diary pages"));
        message.setMessage("Diary page has been removed successfully. To see others profile's diary pages click on the following link.");

        return message;
    }

    /////////////// SUPPORTING METHODS /////////////

    private DiaryPage getDiaryPageIfExist(Long profileId, Date date) {
        foodDiaryService.getFoodDiary(profileId);

        Optional<DiaryPage> optional = diaryPageRepository.findById(new DiaryPageId(profileId, date));
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new EntityNotFoundException(DiaryPage.class, "id", profileId.toString(), "date", date.toString());
        }
    }

    private void actualizeNutrientsIntake(DiaryPage diaryPage) {
        List<Meal> meals = mealService.getAllMeals(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString());
        Double actualKcalIntake = 0.0;
        Double actualProteinIntake = 0.0;
        Double actualCarbsIntake = 0.0;
        Double actualFatIntake = 0.0;
        for (Meal m : meals) {
            actualKcalIntake += m.getMealKcal();
            actualProteinIntake += m.getMealProtein();
            actualCarbsIntake += m.getMealCarbs();
            actualFatIntake += m.getMealFat();
        }
        diaryPage.setNutrients(actualKcalIntake, actualProteinIntake, actualCarbsIntake, actualFatIntake);
    }

    private void actualizeCaloricIntakeGoal(DiaryPage diaryPage) {
        diaryPage.setCaloricIntakeGoal(foodDiaryService.getFoodDiary(diaryPage.getId().getProfileId()).getCaloricIntakeGoal());
    }

    /////////////// LINKS  /////////////

    private void addBasicLinks(DiaryPage diaryPage) {
        diaryPage.add(
                linkTo(methodOn(DiaryPageController.class).getDiaryPage(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withSelfRel(),
                linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(diaryPage.getId().getProfileId())).withRel("Get all profile's diary pages"),
                linkTo(methodOn(MealController.class).getAllMeals(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withRel("Get all meals in the diary page"),
                linkTo(methodOn(ProfileController.class).getProfile(diaryPage.getId().getProfileId())).withRel("Back to profile")
        );
    }

    /////////////// METHODS OVERLOADING /////////////

    private void addBasicLinks(List<DiaryPage> diaryPages) {
        diaryPages.forEach(this::addBasicLinks);
    }
}
