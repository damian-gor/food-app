package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.controllerUI.ProfileControllerUI;
import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.repository.ProfileRepository;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private LinkProvider linkProvider;

    @Override
    public List<Profile> getAllProfiles() {

        List<Profile> profiles = profileRepository.findAll();
        linkProvider.addProfileLinks(profiles);
        return profiles;
    }

    @Override
    public Profile getProfile(Long profileId) {
        Profile profile = getProfileIfExist(profileId);
        linkProvider.addProfileLinks(profile);
        return profile;
    }

    @Override
    public Profile updateProfile(Profile profile) {
        Profile updatedProfile = getProfileIfExist(profile.getId());

        updatedProfile.setName(profile.getName());
        updatedProfile.setIntolerance(profile.getIntolerance());
        updatedProfile.setDiet(profile.getDiet());
        updatedProfile.setCuisine(profile.getCuisine());
        updatedProfile.setFavouriteRecipes(profile.getFavouriteRecipes());
        updatedProfile.setFavouriteProducts(profile.getFavouriteProducts());

        linkProvider.addProfileLinks(updatedProfile);
        profileRepository.save(updatedProfile);
        return updatedProfile;
    }

    @Override
    public Profile partialUpdateProfile(Profile profile) {
        Profile updatedProfile = getProfileIfExist(profile.getId());

        if (profile.getName() != null) updatedProfile.setName(profile.getName());
        if (profile.getDiet() != null) updatedProfile.setDiet(profile.getDiet());
        if (profile.getIntolerance() != null) updatedProfile.setIntolerance(profile.getIntolerance());
        if (profile.getCuisine() != null) updatedProfile.setCuisine(profile.getCuisine());
        if (profile.getFavouriteRecipes() != null) {
            Set<Integer> temp = new TreeSet<>();
            if (updatedProfile.getFavouriteRecipes() != null) temp.addAll(updatedProfile.getFavouriteRecipes());
            temp.addAll(profile.getFavouriteRecipes());
            ArrayList<Integer> updatedRecipes = new ArrayList<>(temp);
            updatedProfile.setFavouriteRecipes(updatedRecipes);
        }
        if (profile.getFavouriteProducts() != null) {
            Set<String> temp = new TreeSet<>();
            if (updatedProfile.getFavouriteProducts() != null) temp.addAll(updatedProfile.getFavouriteProducts());
            temp.addAll(profile.getFavouriteProducts());
            ArrayList<String> updatedProducts = new ArrayList<>(temp);
            updatedProfile.setFavouriteProducts(updatedProducts);
        }

        linkProvider.addProfileLinks(updatedProfile);
        profileRepository.save(updatedProfile);
        return updatedProfile;
    }

    @Override
    public Profile addProfile(Profile profile) {
        profileRepository.save(profile);
        linkProvider.addProfileLinks(profile);
        return profile;
    }

    @Override
    public Message removeProfile(Long profileId) {
        getProfileIfExist(profileId);
        profileRepository.deleteById(profileId);
        Message message = new Message("Profile has been removed successfuly. To get all profiles click on the following link.");
        message.add(linkTo(ProfileControllerUI.class).withSelfRel().withRel("Get all profiles"));
        return message;
    }


    @Override
    public Message addToFavourites(Long profileId, String recipesIds, String productsIds) {
        Profile profile = new Profile();
        profile.setId(profileId);

        if (!recipesIds.equals("0")) {
            List<Integer> recipesIdsList = Arrays.stream(recipesIds.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            profile.setFavouriteRecipes((ArrayList) recipesIdsList);
        }
        if (!productsIds.equals("0")) {
            List<String> productsIdsList = Arrays.stream(productsIds.split(",")).collect(Collectors.toList());
            profile.setFavouriteProducts((ArrayList) productsIdsList);
        }

        partialUpdateProfile(profile);

        Message message = new Message("Positions have been added to favourites successfully. To view the entire list of " +
                "favorite recipes and products, click on the following link.");
        linkProvider.addProfileMessageLinks(message, profileId);
        return message;
    }

    @Override
    public Message removeFromFavourites(Long profileId, String recipesIds, String productsIds) {

        Profile updatedProfile = getProfileIfExist(profileId);

        if (!recipesIds.equals("0")) {
            if (recipesIds.equals("all")) {
                updatedProfile.setFavouriteRecipes(new ArrayList<>());
            } else if (updatedProfile.getFavouriteRecipes() != null) {
                List<Integer> recipesToRemove = Arrays.stream(recipesIds.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                List<Integer> recipesAfterRemoving = updatedProfile.getFavouriteRecipes();
                recipesAfterRemoving.removeAll(recipesToRemove);
                updatedProfile.setFavouriteRecipes((ArrayList) recipesAfterRemoving);
            }
        }
        if (!productsIds.equals("0")) {
            if (productsIds.equals("all")) {
                updatedProfile.setFavouriteProducts(new ArrayList<>());
            } else if (updatedProfile.getFavouriteProducts() != null) {
                List<String> productsToRemove = Arrays.stream(productsIds.split(",")).collect(Collectors.toList());
                List<String> productsAfterRemoving = updatedProfile.getFavouriteProducts();
                productsAfterRemoving.removeAll(productsToRemove);
                updatedProfile.setFavouriteProducts((ArrayList) productsAfterRemoving);
            }
        }

        profileRepository.save(updatedProfile);

        Message message = new Message("Positions have been removed from favourites successfully. To view the entire list of favorite " +
                "recipes and products, click on the following link.");
        linkProvider.addProfileMessageLinks(message, profileId);
        return message;
    }

    /////////////// SUPPORTING METHODS /////////////

    private Profile getProfileIfExist(Long profileId) {
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            return profile;
        } else {
            throw new EntityNotFoundException(Profile.class, "id", profileId.toString());
        }
    }
}