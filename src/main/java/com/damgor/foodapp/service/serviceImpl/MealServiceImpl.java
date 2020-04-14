package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.DiaryPageController;
import com.damgor.foodapp.controller.MealController;
import com.damgor.foodapp.controller.ProductController;
import com.damgor.foodapp.controller.ProfileController;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private DiaryPageService diaryPageService;

    @Override
    public List<Meal> getAllMeals(Long profileId, String stringDate) {
        List<Meal> meals = mealRepository.findByIdProfileIdAndIdDate(profileId, Date.valueOf(stringDate));
        addBasicLinks(meals);
        return meals;
    }

    @Override
    public Meal getMeal(Long profileId, String stringDate, Integer mealId) {
        Meal meal = getMealIfExist(profileId, stringDate, mealId);
        addBasicLinks(meal);
        return meal;
    }

    @Override
    public Meal addMeal(Meal meal) {
        Meal newMeal = meal;
        createAndSetProductsList(meal);
        newMeal.getId().setMealNumber(generateMealNumber(newMeal.getId()));
        mealRepository.save(newMeal);
        addBasicLinks(newMeal);
        return newMeal;
    }

    @Override
    public Meal updateMeal(Meal meal) {
        getMealIfExist(meal);
        createAndSetProductsList(meal);
        mealRepository.save(meal);
        addBasicLinks(meal);
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
        addBasicLinks(meal);
        return meal;
    }

    @Override
    public Message removeMeal(Long profileId, String stringDate, Integer mealId) {
        getMealIfExist(profileId, stringDate, mealId);
        mealRepository.deleteById(new MealId(profileId, Date.valueOf(stringDate), mealId));
        Message message = new Message();
        message.add(linkTo(methodOn(MealController.class).getAllMeals(profileId, stringDate)).withRel("Get other meals in the diary page"));
        message.setMessage("Meal has been removed successfully. To see other diary page's meals click on the following link.");
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
        List<MealProduct> mealProducts = new ArrayList<>();
        for (String element : meal.getElements().keySet()) {
            Integer amount = meal.getElements().get(element);
            Product product = productService.getProductById(element);
            mealProducts.add(new MealProduct(product, amount));
        }
        meal.setProducts(mealProducts);
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

    /////////////// LINKS  /////////////

    private void addBasicLinks(Meal meal) {
        if (meal.getLinks().isEmpty()) {
            meal.add(
                    linkTo(methodOn(MealController.class).getMeal(meal.getId().getProfileId(), meal.getId().getDate().toString(), meal.getId().getMealNumber())).withSelfRel(),
                    linkTo(methodOn(MealController.class).getAllMeals(meal.getId().getProfileId(), meal.getId().getDate().toString())).withRel("Get all meals in that diary page"),
                    linkTo(methodOn(DiaryPageController.class).getDiaryPage(meal.getId().getProfileId(), meal.getId().getDate().toString())).withRel("Back to the diary page"),
                    linkTo(methodOn(ProfileController.class).getProfile(meal.getId().getProfileId())).withRel("Back to the profile")
            );
            addProductsLinks(meal);
        }
    }

    private void addProductsLinks(Meal meal) {
        meal.getProducts().forEach(mealProduct -> mealProduct.add(
                linkTo(methodOn(ProductController.class).getProductById(mealProduct.getProductId())).withRel("Get product details")
        ));
    }

    /////////////// METHODS OVERLOADING /////////////

    private Meal getMealIfExist(Meal meal) {
        return getMealIfExist(meal.getId().getProfileId(), meal.getId().getDate().toString(), meal.getId().getMealNumber());
    }

    private void addBasicLinks(List<Meal> meals) {
        meals.forEach(this::addBasicLinks);
    }

}
