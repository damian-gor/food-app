package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.ProfileDetails;
import com.damgor.foodapp.service.ProfileDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/profiles/{profileId}/details")
@PreAuthorize("#profileId == authentication.principal.id or hasRole('ROLE_ADMIN')")
@Api(tags = "4. Profiles.ProfileDetails", description = "Determining height, weight, nutritional goal (e.g. weight loss) or " +
        "activity level. On this basis, the application will calculate the caloric demand recommended to achieve the given goal.")
public class ProfileDetailsController {

    @Autowired
    private ProfileDetailsService detailsService;

    @ApiOperation(value = "Get profile's details",
            response = ProfileDetails.class)
    @GetMapping
    public ResponseEntity<ProfileDetails> getProfileDetails(@PathVariable Long profileId/*, Principal principal*/) {
        return ResponseEntity.ok().body(detailsService.getProfileDetails(profileId));
    }

    @ApiOperation(value = "Add profile's details",
            response = ProfileDetails.class)
    @PostMapping
    public ResponseEntity<ProfileDetails> addProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        details.setProfileId(profileId);
        return ResponseEntity.ok().body(detailsService.addProfileDetails(details));
    }

    @ApiOperation(value = "Overwrite profile's details",
            response = ProfileDetails.class)
    @PutMapping
    public ResponseEntity<ProfileDetails> updateProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        details.setProfileId(profileId);
        return ResponseEntity.ok().body(detailsService.updateProfileDetails(details));
    }

    @ApiOperation(value = "Overwrite selected parameters of profile's details",
            response = ProfileDetails.class)
    @PatchMapping
    public ResponseEntity<ProfileDetails> partialUpdateProfileDetails(@PathVariable Long profileId, @RequestBody ProfileDetails details) {
        details.setProfileId(profileId);
        return ResponseEntity.ok().body(detailsService.partialUpdateProfileDetails(details));
    }

    @ApiOperation(value = "Remove profile's details",
            response = Message.class)
    @DeleteMapping
    public ResponseEntity<Message> removeDetails(@PathVariable Long profileId) {
        return ResponseEntity.ok().body(detailsService.removeDetails(profileId));
    }

}
