package com.damgor.foodapp.service.serviceImpl;

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

@Service
public class DiaryPageServiceImpl implements DiaryPageService {

    @Autowired
    private DiaryPageRepository diaryPageRepository;
    @Autowired
    private FoodDiaryService foodDiaryService;
    @Autowired
    private MealService mealService;
    @Autowired
    private LinkProvider linkProvider;

    @Override
    public List<DiaryPage> getAllDiaryPages(Long profileId) {
        foodDiaryService.getFoodDiary(profileId);
        List<DiaryPage> pages = diaryPageRepository.findByIdProfileId(profileId);
        if (pages.isEmpty()) pages.add(addDiaryPage(profileId, null));
        pages.forEach(p -> {
            actualizeNutrientsIntake(p);
            actualizeCaloricIntakeGoal(p);
            diaryPageRepository.save(p);
        });
        linkProvider.addDiaryPageLinks(pages);
        return pages;
    }

    @Override
    public DiaryPage getDiaryPage(Long profileId, String stringDate) {
        DiaryPage diaryPage = getDiaryPageIfExist(profileId, Date.valueOf(stringDate));
        actualizeNutrientsIntake(diaryPage);
        actualizeCaloricIntakeGoal(diaryPage);
        diaryPageRepository.save(diaryPage);
        linkProvider.addDiaryPageLinks(diaryPage);
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
        linkProvider.addDiaryPageLinks(diaryPage);
        return diaryPage;
    }

    @Override
    public Message removeDiaryPage(Long profileId, String stringDate) {
        Date date = Date.valueOf(stringDate);
        getDiaryPageIfExist(profileId, date);

        diaryPageRepository.deleteById(new DiaryPageId(profileId, date));

        Message message = new Message("Diary page has been removed successfully");
        linkProvider.addDiaryPageMessageLinks(message, profileId);
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

        if (!meals.isEmpty()) {
            for (Meal m : meals) {
            if (m.getElements()!=null) {
                    actualKcalIntake += m.getMealKcal();
                    actualProteinIntake += m.getMealProtein();
                    actualCarbsIntake += m.getMealCarbs();
                    actualFatIntake += m.getMealFat();
                }
            }
        }
        diaryPage.setNutrients(actualKcalIntake, actualProteinIntake, actualCarbsIntake, actualFatIntake);
    }

    private void actualizeCaloricIntakeGoal(DiaryPage diaryPage) {
        diaryPage.setCaloricIntakeGoal(foodDiaryService.getFoodDiary(diaryPage.getId().getProfileId()).getCaloricIntakeGoal());
    }

}