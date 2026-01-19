package com.project.BlogSystem.auth.repository;

import com.project.BlogSystem.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*

User repository includes these methods:

findByUsername(String username): Finds a user by their username.
findByEmail(String email): Finds a user by their email.
existsByUsername(String username): Checks if a user with the given username exists.
existsByEmail(String email): Checks if a user with the given email exists.

*/

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
