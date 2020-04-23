package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.ProfileDetails;
import com.damgor.foodapp.repository.ProfileDetailsRepository;
import com.damgor.foodapp.service.ProfileDetailsService;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileDetailsServiceImpl implements ProfileDetailsService {

    @Autowired
    private ProfileDetailsRepository detailsRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private LinkProvider linkProvider;

    @Override
    public ProfileDetails getProfileDetails(Long profileId) {
        ProfileDetails profileDetails = getProfileDetailsIfExist(profileId);
        linkProvider.addProfileDetailsLinks(profileDetails);
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
        linkProvider.addProfileDetailsLinks(updatedDetails);
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
        linkProvider.addProfileDetailsLinks(updatedDetails);
        return updatedDetails;
    }

    @Override
    public ProfileDetails addProfileDetails(ProfileDetails details) {
        profileService.getProfile(details.getProfileId());
        ProfileDetails profileDetails = new ProfileDetails(details);
        detailsRepository.save(profileDetails);
        linkProvider.addProfileDetailsLinks(profileDetails);
        return profileDetails;
    }

    @Override
    public Message removeDetails(Long profileId) {
        getProfileDetailsIfExist(profileId);
        detailsRepository.deleteById(profileId);
        Message message = new Message("Profile details has been removed successfuly.");
        linkProvider.addProfileDetailsMessageLinks(message, profileId);
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

}