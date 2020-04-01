package com.damgor.foodapp.service;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.model.Recipe;

import java.util.List;

public interface ProfileService {

    List<Profile> getAllProfiles();
    Profile getProfile(long profileId);
    Profile updateProfile(Profile profile);
    Profile addProfile(Profile profile);
    Message removeProfile(long profileId);

}