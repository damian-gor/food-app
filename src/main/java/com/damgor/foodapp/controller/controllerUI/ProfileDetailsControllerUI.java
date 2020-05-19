package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.ProfileDetails;
import com.damgor.foodapp.service.ProfileDetailsService;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProfileDetails> addProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        details.setProfileId(profileId);
        return ResponseEntity.ok().body(detailsService.addProfileDetails(details));
    }

    @PutMapping
    public ResponseEntity<ProfileDetails> updateProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        details.setProfileId(profileId);
        return ResponseEntity.ok().body(detailsService.updateProfileDetails(details));
    }

    @PatchMapping
    public ResponseEntity<ProfileDetails> partialUpdateProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        details.setProfileId(profileId);
        return ResponseEntity.ok().body(detailsService.partialUpdateProfileDetails(details));
    }

    @DeleteMapping
    public ResponseEntity<Message> removeProfile(@PathVariable("profileId") long profileId) {
        return ResponseEntity.ok().body(profileService.removeProfile(profileId));
    }

}
