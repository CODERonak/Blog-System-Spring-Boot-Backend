
package com.project.BlogSystem.auth.security;

import com.project.BlogSystem.auth.exceptions.UserNotFoundException;
import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.auth.repository.AuthUserRepository;
import com.project.BlogSystem.post.exception.UnauthorizedActionException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/*

getLoggedInUser(): method cheks weather user exists or not and is user authenticated or not

getAuthenticatedUser(String username): this method checks weather the user is authorized do a particular action or not.

*/

@Component
@AllArgsConstructor
public class AuthenticatedUser {

    private final AuthUserRepository authUserRepository;

    public AuthUser getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new UserNotFoundException(
                    "No authenticated user found or credentials are not in the expected format.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return authUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        "Authenticated user '" + userDetails.getUsername() + "' not found in database."));
    }

    public AuthUser getAuthenticatedUser(String username) {
        AuthUser loggedInUser = getLoggedInUser();

        if (!loggedInUser.getUsername().equals(username)) {
            throw new UnauthorizedActionException(
                    "Access denied. You are not authorized to perform this action for user '" + username + "'.");
        }

        return loggedInUser;
    }
}
