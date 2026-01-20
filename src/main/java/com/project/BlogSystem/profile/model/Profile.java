package com.project.BlogSystem.profile.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.BlogSystem.auth.model.entity.AuthUser;

import jakarta.persistence.*;
import lombok.*;

/* 

Profile entity class with id, name, bio, authUser Id, and creation date.

Anotations include @Entity, @Table, @Data, @AllArgsConstructor, @NoArgsConstructor, and @Builder for the class

For fields OneToOne, JoinColumn, Column, @Id and @CreationTimestamp

*/


@Entity
@Table(name = "profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String bio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;
}
