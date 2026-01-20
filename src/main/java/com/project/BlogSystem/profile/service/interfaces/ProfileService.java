package com.project.BlogSystem.profile.service.interfaces;

import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.profile.dto.ProfileResponse;
import com.project.BlogSystem.profile.dto.UpdateProfileRequest;

public interface ProfileService {
    ProfileResponse getProfileByUsername(String username);

    ProfileResponse updateProfile(String username, UpdateProfileRequest updateProfileRequest);

    // for interservice communication, not a endpoint for the user
    void createProfile(AuthUser user);
}
