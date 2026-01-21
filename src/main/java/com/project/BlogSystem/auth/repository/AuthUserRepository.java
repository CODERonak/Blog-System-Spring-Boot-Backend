package com.project.BlogSystem.auth.repository;

import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.auth.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*

User repository includes these methods:

findByUsername(String username): Finds a user by their username.
findByEmail(String email): Finds a user by their email.
existsByUsername(String username): Checks if a user with the given username exists.
existsByEmail(String email): Checks if a user with the given email exists.
existsByRole(Role role): Chekcs if a user has the same role as in the Role enum

*/

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    Optional<AuthUser> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByRole(Role role);
}
