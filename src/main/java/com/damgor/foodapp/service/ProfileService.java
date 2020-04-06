package com.damgor.foodapp.service;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;

import java.util.List;

public interface ProfileService {

    List<Profile> getAllProfiles();
    Profile getProfile(Long profileId);
    Profile updateProfile(Profile profile);
    Profile partialUpdateProfile(Profile profile);
    Profile addProfile(Profile profile);
    Message removeProfile(Long profileId);
    Message addToFavourites(Long profileId, String recipesIds, String productsIds);
    Message removeFromFavourites(Long profileId, String recipesIds, String productsIds);

}
