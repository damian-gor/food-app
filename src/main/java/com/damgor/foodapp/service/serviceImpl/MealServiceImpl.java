package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.*;
import com.damgor.foodapp.repository.MealRepository;
import com.damgor.foodapp.service.DiaryPageService;
import com.damgor.foodapp.service.MealService;
import com.damgor.foodapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private DiaryPageService diaryPageService;
    @Autowired
    private LinkProvider linkProvider;

    @Override
    public List<Meal> getAllMeals(Long profileId, String stringDate) {
        List<Meal> meals = mealRepository.findByIdProfileIdAndIdDate(profileId, Date.valueOf(stringDate));
        linkProvider.addMealLinks(meals);
        if (meals.isEmpty()) meals.add(addMeal(new Meal(profileId, Date.valueOf(stringDate))));
        return meals;
    }

    @Override
    public Meal getMeal(Long profileId, String stringDate, Integer mealId) {
        Meal meal = getMealIfExist(profileId, stringDate, mealId);
        linkProvider.addMealLinks(meal);
        return meal;
    }

    @Override
    public Meal addMeal(Meal meal) {
        createAndSetProductsList(meal);
        meal.getId().setMealNumber(generateMealNumber(meal.getId()));
        mealRepository.save(meal);
        linkProvider.addMealLinks(meal);
        return meal;
    }

    @Override
    public Meal updateMeal(Meal meal) {
        getMealIfExist(meal);
        createAndSetProductsList(meal);
        mealRepository.save(meal);
        linkProvider.addMealLinks(meal);
        return meal;
    }

    @Override
    public Meal partialUpdateMeal(Meal meal) {
        Meal oldMeal = getMealIfExist(meal.getId().getProfileId(), meal.getId().getDate().toString(), meal.getId().getMealNumber());
        Map<String, Integer> map = new HashMap<>();
        map.putAll(oldMeal.getElements());
        map.putAll(meal.getElements());
        meal.setElements(map);
        createAndSetProductsList(meal);
        mealRepository.save(meal);
        linkProvider.addMealLinks(meal);
        return meal;
    }

    @Override
    public Message removeMeal(Long profileId, String stringDate, Integer mealId) {
        getMealIfExist(profileId, stringDate, mealId);
        mealRepository.deleteById(new MealId(profileId, Date.valueOf(stringDate), mealId));
        Message message = new Message("Meal has been removed successfully.");
        linkProvider.addMealMessageLinks(message, profileId, stringDate);
        return message;
    }

    /////////////// SUPPORTING METHODS /////////////

    private Meal getMealIfExist(Long profileId, String stringDate, Integer mealId) {
        Date date = diaryPageService.getDiaryPage(profileId, stringDate).getId().getDate();

        Optional<Meal> optional = mealRepository.findById(new MealId(profileId, date, mealId));
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new EntityNotFoundException(DiaryPage.class,
                    "id", profileId.toString(),
                    "date", stringDate,
                    "mealId", mealId.toString());
        }
    }

    private void createAndSetProductsList(Meal meal) {
        if (meal.getElements() != null) {
            List<MealProduct> mealProducts = new ArrayList<>();
            for (String element : meal.getElements().keySet()) {
                Integer amount = meal.getElements().get(element);
                Product product = productService.getProductById(element, 99999);
                mealProducts.add(new MealProduct(product, amount));
            }
            meal.setProducts(mealProducts);
        }
    }

    private Integer generateMealNumber(MealId mealId) {
        List<Meal> meals = mealRepository.findByIdProfileIdAndIdDate(mealId.getProfileId(), mealId.getDate());
        if (meals.isEmpty()) {
            return 1;
        } else {
            Set<Integer> mealNumbers = new HashSet<>();
            meals.forEach(m -> mealNumbers.add(m.getId().getMealNumber()));
            int mealNumber = 0;
            for (int i = 1; i < mealNumbers.size() + 2; i++) {
                if (!mealNumbers.contains(i)) mealNumber = i;
            }
            return mealNumber;
        }
    }

    private void getMealIfExist(Meal meal) {
        getMealIfExist(meal.getId().getProfileId(), meal.getId().getDate().toString(), meal.getId().getMealNumber());
    }
}
