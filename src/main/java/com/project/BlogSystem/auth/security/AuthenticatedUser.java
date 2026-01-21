package com.project.BlogSystem.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.project.BlogSystem.auth.exceptions.InvalidCredentialsException;

/*

This class make sure's that a user details, posts, comments etc cannot be modified by the user who is not even authorized to do so.

*/

@Component
public class AuthenticatedUser {

    public void getAuthenticatedUser(String username) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidCredentialsException("User not authenticated");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!username.equals(userDetails.getUsername())) {
            throw new InvalidCredentialsException("Access denied, you cannot access others user a/c");
        }
    }
}
