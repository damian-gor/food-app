package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.ProfileDetails;
import com.damgor.foodapp.service.ProfileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/profiles/{profileId}/details")
@PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
public class ProfileDetailsController {

    @Autowired
    private ProfileDetailsService detailsService;

    @GetMapping
    public ResponseEntity<ProfileDetails> getProfileDetails(@PathVariable Long profileId/*, Principal principal*/) {
        return ResponseEntity.ok().body(detailsService.getProfileDetails(profileId));
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
    public ResponseEntity<Message> removeDetails(@PathVariable Long profileId) {
        return ResponseEntity.ok().body(detailsService.removeDetails(profileId));
    }

}
