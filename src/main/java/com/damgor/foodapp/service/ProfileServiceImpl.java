package com.damgor.foodapp.service;

import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.exception.EntityNotFoundException;
//import com.damgor.foodapp.exception.ResourceNotFoundException;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.model.Recipe;
import com.damgor.foodapp.repository.ProfileRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public List<Profile> getAllProfiles() {

        List<Profile> profiles = profileRepository.findAll();

        for(Profile p : profiles) {
            Link link = linkTo(methodOn(ProfileController.class).getProfile(p.getId())).withSelfRel();
            profiles.get((int)(long)p.getId() - 1).add(link);
        }
        return profiles;
    }

//    @Override
//    public Profile getProfile(long profileId) {
//
//        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
//
//        if (optionalProfile.isPresent())
//        {
//            Profile profile = optionalProfile.get();
//
//            Link link1 = linkTo(ProfileController.class).withRel("Get all profiles");
//            Link link2 = linkTo(methodOn(ProfileController.class).getProfile(profile.getId())).withSelfRel();
//            profile.add(link1,link2);
//
//            return profile;
//        }
//        else{
//            throw new ResourceNotFoundException("Have not found profile with id : " + profileId);
//        }
//    }
    @Override
    public Profile getProfile(Long profileId) {

        Optional<Profile> optionalProfile = profileRepository.findById(profileId);

        if (optionalProfile.isPresent())
        {
            Profile profile = optionalProfile.get();

            Link link1 = linkTo(ProfileController.class).withRel("Get all profiles");
            Link link2 = linkTo(methodOn(ProfileController.class).getProfile(profile.getId())).withSelfRel();
            profile.add(link1,link2);

            return profile;
        }
        else{
            throw new EntityNotFoundException(Profile.class, "id", profileId.toString());
        }
    }

    @Override
    public Profile updateProfile(Profile profile) {
        Optional<Profile> optionalProfile = profileRepository.findById(profile.getId());

        if (optionalProfile.isPresent())
        {
            Profile updatedProfile = optionalProfile.get();
            updatedProfile.setName(profile.getName());
            updatedProfile.setIntolerance(profile.getIntolerance());
            updatedProfile.setDiet(profile.getDiet());
            updatedProfile.setCuisine(profile.getCuisine());
            updatedProfile.setFavouriteRecipes(profile.getFavouriteRecipes());

            Link link1 = linkTo(ProfileController.class).withRel("Get all profiles");
            Link link2 = linkTo(methodOn(ProfileController.class).getProfile(profile.getId())).withSelfRel();
            updatedProfile.add(link1,link2);

            profileRepository.save(updatedProfile);
            return updatedProfile;
        }
        else{
            throw new EntityNotFoundException(Profile.class, "id", profile.getId().toString());
        }
    }

    @Override
    public Profile partialUpdateProfile(Profile profile) {
        Optional<Profile> optionalProfile = profileRepository.findById(profile.getId());

        if (optionalProfile.isPresent())
        {
            Profile updatedProfile = optionalProfile.get();
            if (profile.getName()!=null) updatedProfile.setName(profile.getName());
            if (profile.getDiet()!=null) updatedProfile.setDiet(profile.getDiet());
            if (profile.getIntolerance()!=null) updatedProfile.setIntolerance(profile.getIntolerance());
            if (profile.getCuisine()!=null) updatedProfile.setCuisine(profile.getCuisine());
            if (profile.getFavouriteRecipes()!=null) {
                Set<Integer> temp = new TreeSet<>();
                temp.addAll(updatedProfile.getFavouriteRecipes());
                temp.addAll(profile.getFavouriteRecipes());
                ArrayList<Integer> updatedRecipes = new ArrayList<>();
                updatedRecipes.addAll(temp);
                updatedProfile.setFavouriteRecipes(updatedRecipes);
            }

            Link link1 = linkTo(ProfileController.class).withRel("Get all profiles");
            Link link2 = linkTo(methodOn(ProfileController.class).getProfile(profile.getId())).withSelfRel();
            updatedProfile.add(link1,link2);

            profileRepository.save(updatedProfile);
            return updatedProfile;
        }
        else{
            throw new EntityNotFoundException(Profile.class, "id", profile.getId().toString());
        }
    }

    @Override
    public Profile addProfile(Profile profile) {
        profileRepository.save(profile);

        Link link2 = linkTo(ProfileController.class).withSelfRel().withRel("Get all profiles");
        Link link1 = linkTo(methodOn(ProfileController.class).getProfile(profile.getId())).withSelfRel();
        profile.add(link1,link2);

        return profile;
    }

    @Override
    public Message removeProfile(Long profileId) {
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        if (optionalProfile.isPresent()){
            profileRepository.deleteById(profileId);
            Message message = new Message();
            message.setLink(linkTo(ProfileController.class).withSelfRel().withRel("Get all profiles"));
            message.setMessage("Profile has been removed successfuly. To get all profiles click on following link.");
            return message;
        }
        else {
            throw new EntityNotFoundException(Profile.class, "id", profileId.toString());
        }
    }

}
