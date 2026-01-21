package com.project.BlogSystem.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.profile.model.Profile;

import java.util.Optional;

/*

Profile repository for database operations
include method:
* findByUser_Username(String username): finds the username of the particular user for the profile 
* existsByUser(AuthUser user): check weather the user exists
*/

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser_Username(String username);

    boolean existsByUser(AuthUser user);

}
