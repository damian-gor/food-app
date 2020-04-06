package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok().body(profileService.getAllProfiles());
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfile(@PathVariable("profileId") long profileId) {
        return ResponseEntity.ok().body(profileService.getProfile(profileId));
    }

    @PostMapping
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
        return ResponseEntity.ok().body(profileService.addProfile(profile));
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable("profileId") long profileId, @RequestBody Profile profile) {
        profile.setId(profileId);
        return ResponseEntity.ok().body(profileService.updateProfile(profile));
    }

    @PatchMapping("/{profileId}")
    public ResponseEntity<Profile> partialUpdateProfile(@PathVariable("profileId") long profileId, @RequestBody Profile profile) {
        profile.setId(profileId);
        return ResponseEntity.ok().body(profileService.partialUpdateProfile(profile));
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Message> removeProfile(@PathVariable("profileId") long profileId) {
        return ResponseEntity.ok().body(profileService.removeProfile(profileId));
    }

    @PutMapping("/{profileId}/favourites")
    public ResponseEntity<Message> addToFavourites(@PathVariable long profileId,
                                      @RequestParam(defaultValue = "0") String recipesIds,
                                      @RequestParam(defaultValue = "0") String productsIds) {
        return ResponseEntity.ok().body(profileService.addToFavourites(profileId, recipesIds, productsIds));
    }

    @DeleteMapping("/{profileId}/favourites")
    public ResponseEntity<Message> removeFromFavourites(@PathVariable long profileId,
                                                   @RequestParam(defaultValue = "0") String recipesIds,
                                                   @RequestParam(defaultValue = "0") String productsIds) {
        return ResponseEntity.ok().body(profileService.removeFromFavourites(profileId, recipesIds, productsIds));
    }
}
