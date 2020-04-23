package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.*;
import com.damgor.foodapp.controller.controllerUI.*;
import com.damgor.foodapp.model.*;
import org.springframework.hateoas.Link;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LinkProvider {

    public LinkProvider() {
    }

    private String getRequestURI() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request.getRequestURI();
        }
        return "0";
    }


    /////////////// RECIPES  /////////////

    public void addRecipesLinks(Recipe recipe, long userId) {
        if (getRequestURI().contains("/ui")) {
            recipe.add(new Link(recipe.getRecipeURL().toString()).withRel("Source"));
            if (userId != 99999)
                recipe.add(linkTo(methodOn(ProfileControllerUI.class).addToFavourites(userId, recipe.getId().toString(), null))
                        .withRel("Add to favourites"));
        } else {
            recipe.add(linkTo(methodOn(RecipeController.class).getRecipeById(recipe.getId(), null)).withSelfRel());
            if (userId != 99999)
                recipe.add(linkTo(methodOn(ProfileController.class).addToFavourites(userId, recipe.getId().toString(), null))
                        .withRel("Add to favourites"));
        }
    }

    public void addRecipesLinks(List<ShortRecipe> recipes, long userId) {
        if (getRequestURI().contains("/ui")) {
            recipes.forEach(r -> r.add(linkTo(methodOn(RecipeControllerUI.class).getRecipeById(r.getId(), null)).withRel("Get details")));
            if (userId != 99999)
                recipes.forEach(r -> r.add(linkTo(methodOn(ProfileControllerUI.class).addToFavourites(userId, r.getId().toString(), null))
                        .withRel("Add to favourites")));
        } else {
            recipes.forEach(r -> r.add(linkTo(methodOn(RecipeController.class).getRecipeById(r.getId(), null)).withRel("Get details")));
            if (userId != 99999)
                recipes.forEach(r -> r.add(linkTo(methodOn(ProfileController.class).addToFavourites(userId, r.getId().toString(), null))
                        .withRel("Add to favourites")));
        }
    }

    /////////////// PRODUCTS  /////////////

    public void addProductsLinks(Product product, long userId) {
        if (getRequestURI().contains("/ui")) {
            if (userId != 99999)
                product.add(linkTo(methodOn(ProfileControllerUI.class).addToFavourites(userId, null, product.getId()))
                        .withRel("Add to favourites"));
        } else {
//            product.add(linkTo(methodOn(ProductController.class).getProductById(product.getId(), null)).withSelfRel());
            if (userId != 99999)
                product.add(linkTo(methodOn(ProfileController.class).addToFavourites(userId, null, product.getId()))
                        .withRel("Add to favourites"));
        }
    }

    public void addProductsLinks(List<ShortProduct> products, long userId) {
        if (getRequestURI().contains("/ui")) {
            products.forEach(p -> p.add(
                    linkTo(methodOn(ProductControllerUI.class).getProductById(p.getId().toString(), null)).withRel("Get details")));
            if (userId != 99999)
                products.forEach(p -> p.add(linkTo(methodOn(ProfileControllerUI.class).addToFavourites(userId, null, p.getId().toString()))
                        .withRel("Add to favourites")));
        } else {
            products.forEach(p -> p.add(
                    linkTo(methodOn(ProductController.class).getProductById(p.getId().toString(), null)).withRel("Get details")));
            if (userId != 99999)
                products.forEach(p -> p.add(linkTo(methodOn(ProfileController.class).addToFavourites(userId, null, p.getId().toString()))
                        .withRel("Add to favourites")));
        }
    }

    /////////////// PROFILES /////////////

    public void addProfileLinks(Profile profile) {

        if (getRequestURI().contains("/ui")) {
            profile.add(
                    linkTo(methodOn(ProfileControllerUI.class).getProfile(profile.getId())).withSelfRel(),
                    linkTo(methodOn(ProfileDetailsControllerUI.class).getProfileDetails(profile.getId())).withRel("Go to profile details"),
                    linkTo(methodOn(FoodDiaryControllerUI.class).getFoodDiary(profile.getId())).withRel("Go to profile food diary"),
                    linkTo(ProfileControllerUI.class).withSelfRel().withRel("Get all profiles")
            );
        } else {
            profile.add(
                    linkTo(methodOn(ProfileController.class).getProfile(profile.getId())).withSelfRel(),
                    linkTo(methodOn(ProfileDetailsController.class).getProfileDetails(profile.getId())).withRel("Go to profile details"),
                    linkTo(methodOn(FoodDiaryController.class).getFoodDiary(profile.getId())).withRel("Go to profile food diary"),
                    linkTo(ProfileController.class).withSelfRel().withRel("Get all profiles")
            );
        }
    }

    public void addProfileLinks(List<Profile> profiles) {
        profiles.forEach(this::addProfileLinks);
    }

    public void addProfileMessageLinks(Message message, Long profileId) {
        Profile profile = new Profile();
        profile.setId(profileId);
        addProfileLinks(profile);
        message.add(profile.getLinks());
    }

    /////////////// PROFILE DETAILS  /////////////

    public void addProfileDetailsLinks(ProfileDetails profileDetails) {
        if (getRequestURI().contains("/ui")) {
            profileDetails.add(
                    linkTo(methodOn(ProfileDetailsControllerUI.class).getProfileDetails(profileDetails.getProfileId())).withSelfRel(),
                    linkTo(methodOn(ProfileControllerUI.class).getProfile(profileDetails.getProfileId())).withRel("Back to profile")
            );
        } else {
            profileDetails.add(
                    linkTo(methodOn(ProfileDetailsController.class).getProfileDetails(profileDetails.getProfileId())).withSelfRel(),
                    linkTo(methodOn(ProfileController.class).getProfile(profileDetails.getProfileId())).withRel("Back to profile")
            );
        }
    }

    public void addProfileDetailsMessageLinks(Message message, long profileId) {
        if (getRequestURI().contains("/ui")) {
            message.add(linkTo(methodOn(ProfileControllerUI.class).getProfile(profileId)).withRel("Back to profile"));
        } else {
            message.add(linkTo(methodOn(ProfileController.class).getProfile(profileId)).withRel("Back to profile")
            );
        }
    }

    /////////////// FOOD DIARY  /////////////

    public void addFoodDiaryLinks(FoodDiary foodDiary) {
        if (getRequestURI().contains("/ui")) {
            foodDiary.add(
                    linkTo(methodOn(FoodDiaryControllerUI.class).getFoodDiary(foodDiary.getProfileId())).withSelfRel(),
                    linkTo(methodOn(DiaryPageControllerUI.class).getAllDiaryPages(foodDiary.getProfileId())).withRel("Get all profile diary pages"),
                    linkTo(methodOn(ProfileDetailsControllerUI.class).getProfileDetails(foodDiary.getProfileId())).withRel("Go to profile details"),
                    linkTo(methodOn(ProfileControllerUI.class).getProfile(foodDiary.getProfileId())).withRel("Back to profile")
            );
        } else {
            foodDiary.add(
                    linkTo(methodOn(FoodDiaryController.class).getFoodDiary(foodDiary.getProfileId())).withSelfRel(),
                    linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(foodDiary.getProfileId())).withRel("Get all profile diary pages"),
                    linkTo(methodOn(ProfileDetailsController.class).getProfileDetails(foodDiary.getProfileId())).withRel("Go to profile details"),
                    linkTo(methodOn(ProfileController.class).getProfile(foodDiary.getProfileId())).withRel("Back to profile")
            );
        }
    }

    public void addFoodDiaryMessageLinks(Message message, long profileId) {
        if (getRequestURI().contains("/ui")) {
            message.add(linkTo(methodOn(ProfileControllerUI.class).getProfile(profileId)).withRel("Back to profile"));
        } else {
            message.add(linkTo(methodOn(ProfileController.class).getProfile(profileId)).withRel("Back to profile")
            );
        }
    }

    /////////////// DIARY PAGES  /////////////

    public void addDiaryPageLinks(DiaryPage diaryPage) {
        if (getRequestURI().contains("/ui")) {
            diaryPage.add(
                    linkTo(methodOn(DiaryPageControllerUI.class).getDiaryPage(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withSelfRel(),
                    linkTo(methodOn(DiaryPageControllerUI.class).getAllDiaryPages(diaryPage.getId().getProfileId())).withRel("Get all profile diary pages"),
                    linkTo(methodOn(MealControllerUI.class).getAllMeals(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withRel("Get all meals in the diary page"),
                    linkTo(methodOn(ProfileControllerUI.class).getProfile(diaryPage.getId().getProfileId())).withRel("Back to profile"),
                    linkTo(methodOn(FoodDiaryControllerUI.class).getFoodDiary(diaryPage.getId().getProfileId())).withRel("Go to profile food diary")
            );
        } else {
            diaryPage.add(
                    linkTo(methodOn(DiaryPageController.class).getDiaryPage(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withSelfRel(),
                    linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(diaryPage.getId().getProfileId())).withRel("Get all profile diary pages"),
                    linkTo(methodOn(MealController.class).getAllMeals(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withRel("Get all meals in the diary page"),
                    linkTo(methodOn(ProfileController.class).getProfile(diaryPage.getId().getProfileId())).withRel("Back to profile"),
                    linkTo(methodOn(FoodDiaryController.class).getFoodDiary(diaryPage.getId().getProfileId())).withRel("Go to profile food diary")
            );
        }
    }

    public void addDiaryPageMessageLinks(Message message, long profileId) {
        if (getRequestURI().contains("/ui")) {
            message.add(linkTo(methodOn(DiaryPageControllerUI.class).getAllDiaryPages(profileId)).withRel("Get all profile's diary pages"),
                    linkTo(methodOn(ProfileControllerUI.class).getProfile(profileId)).withRel("Back to profile"));
        } else {
            message.add(linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(profileId)).withRel("Get all profile's diary pages"),
                    linkTo(methodOn(ProfileController.class).getProfile(profileId)).withRel("Back to profile"));
        }
    }

    public void addDiaryPageLinks(List<DiaryPage> diaryPages) {
        diaryPages.forEach(this::addDiaryPageLinks);
    }

    /////////////// MEALS  /////////////

    public void addMealLinks(Meal meal) {
        if (getRequestURI().contains("/ui")) {
            if (meal.getLinks().isEmpty()) {
                meal.add(
                        linkTo(methodOn(MealControllerUI.class).getMeal(meal.getId().getProfileId(), meal.getId().getDate().toString(), meal.getId().getMealNumber())).withSelfRel(),
                        linkTo(methodOn(MealControllerUI.class).getAllMeals(meal.getId().getProfileId(), meal.getId().getDate().toString())).withRel("Get all meals in that diary page"),
                        linkTo(methodOn(DiaryPageControllerUI.class).getDiaryPage(meal.getId().getProfileId(), meal.getId().getDate().toString())).withRel("Back to the diary page"),
                        linkTo(methodOn(ProfileControllerUI.class).getProfile(meal.getId().getProfileId())).withRel("Back to the profile"),
                        linkTo(methodOn(FoodDiaryControllerUI.class).getFoodDiary(meal.getId().getProfileId())).withRel("Go to profile food diary")
                );
                if (meal.getElements() != null) meal.getProducts().forEach(mealProduct -> mealProduct.add(
                        linkTo(methodOn(ProductControllerUI.class).getProductById(mealProduct.getProductId(), null)).withRel("Get product details")
                ));
            } else {
                if (meal.getLinks().isEmpty()) {
                    meal.add(
                            linkTo(methodOn(MealController.class).getMeal(meal.getId().getProfileId(), meal.getId().getDate().toString(), meal.getId().getMealNumber())).withSelfRel(),
                            linkTo(methodOn(MealController.class).getAllMeals(meal.getId().getProfileId(), meal.getId().getDate().toString())).withRel("Get all meals in that diary page"),
                            linkTo(methodOn(DiaryPageController.class).getDiaryPage(meal.getId().getProfileId(), meal.getId().getDate().toString())).withRel("Back to the diary page"),
                            linkTo(methodOn(ProfileController.class).getProfile(meal.getId().getProfileId())).withRel("Back to the profile"),
                            linkTo(methodOn(FoodDiaryController.class).getFoodDiary(meal.getId().getProfileId())).withRel("Go to profile food diary")
                    );
                    if (meal.getElements() != null) meal.getProducts().forEach(mealProduct -> mealProduct.add(
                            linkTo(methodOn(ProductController.class).getProductById(mealProduct.getProductId(), null)).withRel("Get product details")
                    ));
                }
            }
        }
    }

    public void addMealMessageLinks(Message message, long profileId, String stringDate) {
        if (getRequestURI().contains("/ui")) {
            message.add(linkTo(methodOn(MealControllerUI.class).getAllMeals(profileId, stringDate)).withRel("Get other meals in the diary page"),
                    linkTo(methodOn(ProfileControllerUI.class).getProfile(profileId)).withRel("Back to profile"));
        } else {
            message.add(linkTo(methodOn(MealController.class).getAllMeals(profileId, stringDate)).withRel("Get other meals in the diary page"),
                    linkTo(methodOn(ProfileController.class).getProfile(profileId)).withRel("Back to profile"));
        }
    }

    public void addMealLinks(List<Meal> meals) {
        meals.forEach(this::addMealLinks);
    }

}