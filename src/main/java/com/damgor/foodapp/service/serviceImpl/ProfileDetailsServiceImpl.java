package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.controller.ProfileDetailsController;
import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.ProfileDetails;
import com.damgor.foodapp.repository.ProfileDetailsRepository;
import com.damgor.foodapp.service.ProfileDetailsService;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProfileDetailsServiceImpl implements ProfileDetailsService {

    @Autowired
    private ProfileDetailsRepository detailsRepository;

    @Autowired
    private ProfileService profileService;

    @Override
    public ProfileDetails getProfileDetails(Long profileId) {
        ProfileDetails profileDetails = getProfileDetailsIfExist(profileId);
        addBasicLinks(profileDetails);
        return profileDetails;
    }

    @Override
    public ProfileDetails updateProfileDetails(ProfileDetails details) {
        ProfileDetails updatedDetails = getProfileDetailsIfExist(details.getProfileId());

        updatedDetails.setHeight(details.getHeight());
        updatedDetails.setWeight(details.getWeight());
        updatedDetails.setAim(details.getAim());
        updatedDetails.setActivityLevel(details.getActivityLevel());
        updatedDetails.setYearOfBirth(details.getYearOfBirth());
        updatedDetails.setSex(details.getSex());

        detailsRepository.save(updatedDetails);

        addBasicLinks(updatedDetails);
        return updatedDetails;
    }

    @Override
    public ProfileDetails partialUpdateProfileDetails(ProfileDetails details) {
        ProfileDetails updatedDetails = getProfileDetailsIfExist(details.getProfileId());

        if (details.getHeight() != null) updatedDetails.setHeight(details.getHeight());
        if (details.getWeight() != null) updatedDetails.setWeight(details.getWeight());
        if (details.getAim() != null) updatedDetails.setAim(details.getAim());
        if (details.getActivityLevel() != null) updatedDetails.setActivityLevel(details.getActivityLevel());
        if (details.getYearOfBirth() != null) updatedDetails.setYearOfBirth(details.getYearOfBirth());
        if (details.getSex() != null) updatedDetails.setSex(details.getSex());

        detailsRepository.save(updatedDetails);

        addBasicLinks(updatedDetails);

        return updatedDetails;
    }

    @Override
    public ProfileDetails addProfileDetails(ProfileDetails details) {
        profileService.getProfile(details.getProfileId());
        ProfileDetails profileDetails = new ProfileDetails(details);
        detailsRepository.save(profileDetails);
        addBasicLinks(profileDetails);
        return profileDetails;
    }

    @Override
    public Message removeDetails(Long profileId) {
        getProfileDetailsIfExist(profileId);
        detailsRepository.deleteById(profileId);
        Message message = new Message("Profile details has been removed successfuly. To get back to profile click on the following link.");
        message.add(linkTo(methodOn(ProfileController.class).getProfile(profileId)).withRel("Back to profile."));
        return message;
    }

    /////////////// SUPPORTING METHODS /////////////

    private ProfileDetails getProfileDetailsIfExist(Long profileId) {
        profileService.getProfile(profileId);
        Optional<ProfileDetails> optionalDetails = detailsRepository.findById(profileId);
        if (optionalDetails.isPresent()) {
            return optionalDetails.get();
        } else throw new EntityNotFoundException(ProfileDetails.class, "id", profileId.toString());
    }

    /////////////// LINKS  /////////////

    private void addBasicLinks(ProfileDetails profileDetails) {
        profileDetails.add(
                linkTo(methodOn(ProfileDetailsController.class).getProfileDetails(profileDetails.getProfileId())).withSelfRel(),
                linkTo(methodOn(ProfileController.class).getProfile(profileDetails.getProfileId())).withRel("Back to profile.")
        );
    }
}