package com.project.BlogSystem.profile.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.auth.security.AuthenticatedUser;
import com.project.BlogSystem.profile.dto.ProfileResponse;
import com.project.BlogSystem.profile.dto.UpdateProfileRequest;
import com.project.BlogSystem.profile.exception.ProfileAlreadyExistsException;
import com.project.BlogSystem.profile.exception.ProfileNotFoundException;
import com.project.BlogSystem.profile.mapper.ProfileMapper;
import com.project.BlogSystem.profile.model.Profile;
import com.project.BlogSystem.profile.repository.ProfileRepository;
import com.project.BlogSystem.profile.service.interfaces.ProfileService;

import lombok.AllArgsConstructor;

/*

Profile service impl include methods
* ProfileResponse getProfileByUsername(String username)
* ProfileResponse updateProfile(String username, UpdateProfileRequest updateProfileRequest)
* void createProfile(AuthUser user)
*/

@Service
@AllArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final AuthenticatedUser authenticatedUser;

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfileByUsername(String username) {

        Profile profile = profileRepository.findByUser_Username(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found by the username: " + username));

        return profileMapper.profileToProfileResponse(profile);
    }

    @Override
    public ProfileResponse updateProfile(String username, UpdateProfileRequest updateProfileRequest) {

        authenticatedUser.getAuthenticatedUser(username);

        Profile profile = profileRepository.findByUser_Username(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile Not found"));

        profileMapper.updateProfileRequest(updateProfileRequest, profile);

        profileRepository.save(profile);

        return profileMapper.profileToProfileResponse(profile);
    }

    // Create a profile for a user
    // it's not going to be a public endpoint
    // This methods executes when the user creates it's a/c in AuthServiceImpl
    @Override
    public void createProfile(AuthUser user) {
        if (profileRepository.existsByUser(user)) {
            throw new ProfileAlreadyExistsException("Profile alraedy exists");
        }

        Profile profile = Profile.builder()
                .user(user)
                .name(user.getUsername())
                .build();

        profileRepository.save(profile);
    }
}
