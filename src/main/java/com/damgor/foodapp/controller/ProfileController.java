package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    ProfileService profileService;

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

//    @DeleteMapping("/{profileId}")
//    public HttpStatus removeProfile(@PathVariable("profileId") long profileId) {
//        profileService.removeProfile(profileId);
//        return HttpStatus.OK;
//    }

}
