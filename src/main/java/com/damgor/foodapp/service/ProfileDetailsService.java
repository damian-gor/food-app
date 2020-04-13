package com.damgor.foodapp.service;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.ProfileDetails;

public interface ProfileDetailsService {
    ProfileDetails getProfileDetails (Long profileId);
    ProfileDetails updateProfileDetails(ProfileDetails details);
    ProfileDetails partialUpdateProfileDetails(ProfileDetails details);
    ProfileDetails addProfileDetails(ProfileDetails details);
    Message removeDetails(Long profileId);
}
