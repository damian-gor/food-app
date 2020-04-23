package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.ProfileDetails;
import com.damgor.foodapp.service.ProfileDetailsService;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/ui/profiles/{profileId}/details")
@PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
@ApiIgnore
public class ProfileDetailsControllerUI {

    @Autowired
    private ProfileDetailsService detailsService;
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ModelAndView getProfileDetails(@PathVariable Long profileId) {
        ModelAndView modelAndView = new ModelAndView("profile-details");
        ProfileDetails profileDetails = detailsService.getProfileDetails(profileId);
        String profileName = profileService.getProfile(profileId).getName();
        modelAndView.addObject("profileName", profileName);
        modelAndView.addObject("profileDetails", profileDetails);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView addProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        ModelAndView modelAndView = new ModelAndView("profile-details");
        details.setProfileId(profileId);
        ProfileDetails profileDetails = detailsService.addProfileDetails(details);
        String profileName = profileService.getProfile(profileId).getName();
        modelAndView.addObject("profileName", profileName);
        modelAndView.addObject("profileDetails", profileDetails);
        return modelAndView;
    }

    @PutMapping
    public ModelAndView updateProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        ModelAndView modelAndView = new ModelAndView("profile-details");
        details.setProfileId(profileId);
        ProfileDetails profileDetails = detailsService.updateProfileDetails(details);
        String profileName = profileService.getProfile(profileId).getName();
        modelAndView.addObject("profileName", profileName);
        modelAndView.addObject("profileDetails", profileDetails);
        return modelAndView;
    }

    @PatchMapping
    public ModelAndView partialUpdateProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        ModelAndView modelAndView = new ModelAndView("profile-details");
        details.setProfileId(profileId);
        ProfileDetails profileDetails = detailsService.partialUpdateProfileDetails(details);
        String profileName = profileService.getProfile(profileId).getName();
        modelAndView.addObject("profileName", profileName);
        modelAndView.addObject("profileDetails", profileDetails);
        return modelAndView;
    }

    @DeleteMapping
    public ModelAndView removeDetails(@PathVariable Long profileId) {
        ModelAndView modelAndView = new ModelAndView("message");
        Message message = detailsService.removeDetails(profileId);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

}
