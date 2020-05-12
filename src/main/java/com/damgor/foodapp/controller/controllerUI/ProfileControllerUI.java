package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/ui/profiles")
@ApiIgnore
public class ProfileControllerUI {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ModelAndView getAllProfiles() {
        ModelAndView modelAndView = new ModelAndView("profiles");
        List<Profile> profiles = profileService.getAllProfiles();
        modelAndView.addObject("profiles", profiles);
        return modelAndView;
    }

    @GetMapping("/{profileId}")
    public ModelAndView getProfile(@PathVariable("profileId") long profileId) {
        ModelAndView modelAndView = new ModelAndView("profile");
        Profile profile = profileService.getProfile(profileId);
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity <Profile> addProfile(@RequestBody Profile profile) {
        return ResponseEntity.ok().body(profileService.addProfile(profile));
    }

    @PutMapping("/{profileId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ModelAndView updateProfile(@PathVariable("profileId") long profileId, @RequestBody Profile profile) {
        ModelAndView modelAndView = new ModelAndView("profile");
        profile.setId(profileId);
        Profile updatedProfile = profileService.updateProfile(profile);
        modelAndView.addObject("profile", updatedProfile);
        return modelAndView;
    }

    @PatchMapping("/{profileId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Profile> partialUpdateProfile(@PathVariable("profileId") long profileId, @RequestBody Profile profile) {
        profile.setId(profileId);
        return ResponseEntity.ok().body(profileService.partialUpdateProfile(profile));
    }

    @DeleteMapping("/{profileId}")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeProfile(@PathVariable("profileId") long profileId) {
        return ResponseEntity.ok().body(profileService.removeProfile(profileId));
    }

    @GetMapping("/{profileId}/favourites")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> addToFavourites(@PathVariable long profileId,
                                                   @RequestParam(defaultValue = "0") String recipesIds,
                                                   @RequestParam(defaultValue = "0") String productsIds) {
        return ResponseEntity.ok().body(profileService.addToFavourites(profileId, recipesIds, productsIds));
    }

    @DeleteMapping("/{profileId}/favourites")
    @PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Message> removeFromFavourites(@PathVariable long profileId,
                                                        @RequestParam(defaultValue = "0") String recipesIds,
                                                        @RequestParam(defaultValue = "0") String productsIds) {
        return ResponseEntity.ok().body(profileService.removeFromFavourites(profileId, recipesIds, productsIds));
    }
}
