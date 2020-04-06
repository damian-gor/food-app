package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.controller.ProfileDetailsController;
import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.model.ProfileDetails;
import com.damgor.foodapp.repository.ProfileDetailsRepository;
import com.damgor.foodapp.repository.ProfileRepository;
import com.damgor.foodapp.service.ProfileDetailsService;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProfileDetailsServiceImpl implements ProfileDetailsService {

    @Autowired
    private ProfileDetailsRepository detailsRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Override
    public ProfileDetails getProfileDetails(Long profileId) {
        profileService.getProfile(profileId);
        Optional<ProfileDetails> optionalDetails = detailsRepository.findById(profileId);
        if (optionalDetails.isPresent()) {
            ProfileDetails profileDetails = optionalDetails.get();
            profileDetails.add(
                    linkTo(methodOn(ProfileController.class).getProfile(profileId)).withRel("Back to profile."),
                    linkTo(methodOn(ProfileDetailsController.class).getProfileDetails(profileId)).withSelfRel()
            );
            return profileDetails;
        }
        else throw new EntityNotFoundException(ProfileDetails.class, "id", profileId.toString());
    }

    @Override
    public ProfileDetails updateProfileDetails(ProfileDetails details) {
        profileService.getProfile(details.getId());
        Optional<ProfileDetails> optionalDetails = detailsRepository.findById(details.getId());
        if (optionalDetails.isPresent()) {
            ProfileDetails updatedDetails = optionalDetails.get();
            updatedDetails.setHeight(details.getHeight());
            updatedDetails.setWeight(details.getWeight());
            updatedDetails.setAim(details.getAim());
            updatedDetails.setActivityLevel(details.getActivityLevel());
            updatedDetails.setYearOfBirth(details.getYearOfBirth());
            updatedDetails.setSex(details.getSex());

            detailsRepository.save(updatedDetails);

            updatedDetails = detailsRepository.findById(updatedDetails.getId()).get();

            updatedDetails.add(
                    linkTo(methodOn(ProfileController.class).getProfile(details.getId())).withRel("Back to profile."),
                    linkTo(methodOn(ProfileDetailsController.class).getProfileDetails(details.getId())).withSelfRel()
            );
            return updatedDetails;
        } else {
            throw new EntityNotFoundException(ProfileDetails.class, "id", details.getId().toString());
        }
    }

    @Override
    public ProfileDetails partialUpdateProfileDetails(ProfileDetails details) {
        profileService.getProfile(details.getId());
        Optional<ProfileDetails> optionalDetails = detailsRepository.findById(details.getId());
        if (optionalDetails.isPresent()) {
            ProfileDetails updatedDetails = optionalDetails.get();
            if (details.getHeight() != null) updatedDetails.setHeight(details.getHeight());
            if (details.getWeight() != null) updatedDetails.setWeight(details.getWeight());
            if (details.getAim() != null) updatedDetails.setAim(details.getAim());
            if (details.getActivityLevel() != null) updatedDetails.setActivityLevel(details.getActivityLevel());
            if (details.getYearOfBirth() != null) updatedDetails.setYearOfBirth(details.getYearOfBirth());
            if (details.getSex() != null) updatedDetails.setSex(details.getSex());

            detailsRepository.save(updatedDetails);

            updatedDetails = detailsRepository.findById(updatedDetails.getId()).get();

            updatedDetails.add(
                    linkTo(methodOn(ProfileController.class).getProfile(details.getId())).withRel("Back to profile."),
                    linkTo(methodOn(ProfileDetailsController.class).getProfileDetails(details.getId())).withSelfRel()
            );
            return updatedDetails;
        } else {
            throw new EntityNotFoundException(ProfileDetails.class, "id", details.getId().toString());
        }
    }

    @Override
    public ProfileDetails addProfileDetails(ProfileDetails details) {
        profileService.getProfile(details.getId());
        ProfileDetails profileDetails = new ProfileDetails(details);
        detailsRepository.save(profileDetails);

        profileDetails.add(
                linkTo(methodOn(ProfileController.class).getProfile(details.getId())).withRel("Back to profile."),
                linkTo(methodOn(ProfileDetailsController.class).getProfileDetails(details.getId())).withSelfRel()
        );
        return profileDetails;
    }

    @Override
    public Message removeDetails(Long profileId) {
        profileService.getProfile(profileId);
        Optional<ProfileDetails> optionalDetails = detailsRepository.findById(profileId);
        if (optionalDetails.isPresent()) {
            detailsRepository.deleteById(profileId);
            Message message = new Message();
            message.setLink(linkTo(methodOn(ProfileController.class).getProfile(profileId)).withRel("Back to profile."));
            message.setMessage("Profile details has been removed successfuly. To get back to profile click on the following link.");
            return message;
        } else {
            throw new EntityNotFoundException(ProfileDetails.class, "id", profileId.toString());
        }
    }
}