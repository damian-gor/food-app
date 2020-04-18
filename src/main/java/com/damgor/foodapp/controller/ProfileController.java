package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@Api(tags = "3. Profiles", description = "The ability to manage profiles, specifying the name, type of diet, intolerances," +
        " preferred cuisine (choosing the available options, thanks to which you can then customize the recipe search). " +
        "You can add your favorite products and recipes to the profile.")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "Get a list of all profiles",
            response = Profile.class)
    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok().body(profileService.getAllProfiles());
    }

    @ApiOperation(value = "Get the profile by id",
            response = Profile.class)
    @GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfile(@PathVariable("profileId") long profileId) {
        return ResponseEntity.ok().body(profileService.getProfile(profileId));
    }

    @ApiOperation(value = "Add profile",
            response = Profile.class)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
        return ResponseEntity.ok().body(profileService.addProfile(profile));
    }

    @ApiOperation(value = "Overwrite the profile",
            response = Profile.class)
    @PutMapping("/{profileId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Profile> updateProfile(@PathVariable("profileId") long profileId, @RequestBody Profile profile) {
        profile.setId(profileId);
        return ResponseEntity.ok().body(profileService.updateProfile(profile));
    }

    @ApiOperation(value = "Overwrite selected parameters of the profile",
            response = Profile.class)
    @PatchMapping("/{profileId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Profile> partialUpdateProfile(@PathVariable("profileId") long profileId, @RequestBody Profile profile) {
        profile.setId(profileId);
        return ResponseEntity.ok().body(profileService.partialUpdateProfile(profile));
    }

    @ApiOperation(value = "Overwrite profile",
            response = Message.class)
    @DeleteMapping("/{profileId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeProfile(@PathVariable("profileId") long profileId) {
        return ResponseEntity.ok().body(profileService.removeProfile(profileId));
    }

    @ApiOperation(value = "Add recipes / product to profile's favourites",
            notes = "Insert recipes' or/and product' ids and your profileId (if you're authenticated, the App do it by itself).",
            response = Message.class)
    @PostMapping("/{profileId}/favourites")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> addToFavourites(@PathVariable long profileId,
                                                   @RequestParam(defaultValue = "0") String recipesIds,
                                                   @RequestParam(defaultValue = "0") String productsIds) {
        return ResponseEntity.ok().body(profileService.addToFavourites(profileId, recipesIds, productsIds));
    }

    @ApiOperation(value = "Remove recipes / product to profile's favourites",
            notes = "Insert recipes' or/and product' ids and your profileId (if you're authenticated, the App do it by itself).",
            response = Message.class)
    @DeleteMapping("/{profileId}/favourites")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeFromFavourites(@PathVariable long profileId,
                                                        @RequestParam(defaultValue = "0") String recipesIds,
                                                        @RequestParam(defaultValue = "0") String productsIds) {
        return ResponseEntity.ok().body(profileService.removeFromFavourites(profileId, recipesIds, productsIds));
    }
}
