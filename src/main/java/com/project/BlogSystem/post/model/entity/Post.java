package com.project.BlogSystem.post.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.post.model.enums.PostStatus;

import jakarta.persistence.*;
import lombok.*;

/*

Post entity class with id, author id (foreign key), title, content, PostStatus and creation stamps.

Anotations include @Entity, @Table, @Data, @AllArgsConstructor, @NoArgsConstructor, and @Builder for convenient data handling and database mapping.

@ManyToOne - Many Posts can be written by one author
@Lob - To write long content 

*/

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AuthUser author;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @CreationTimestamp
    private LocalDateTime creationDate;

}
