package com.project.BlogSystem.auth.model.entity;

import com.project.BlogSystem.auth.model.enums.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*

User entity class with id, username, password, email, and role fields.

Anotations include @Entity, @Table, @Data, @AllArgsConstructor, @NoArgsConstructor, and @Builder for convenient data handling and database mapping.
*/

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}