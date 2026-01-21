package com.project.BlogSystem.profile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.BlogSystem.profile.dto.ProfileResponse;
import com.project.BlogSystem.profile.dto.UpdateProfileRequest;
import com.project.BlogSystem.profile.service.interfaces.ProfileService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getProfileByUsername(@PathVariable String username) {
        ProfileResponse response = profileService.getProfileByUsername(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable String username,
            @RequestBody UpdateProfileRequest request) {
        ProfileResponse response = profileService.updateProfile(username, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
