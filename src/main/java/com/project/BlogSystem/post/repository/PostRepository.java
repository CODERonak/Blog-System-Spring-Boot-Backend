package com.project.BlogSystem.post.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.BlogSystem.post.model.entity.Post;
import com.project.BlogSystem.post.model.enums.PostStatus;

/*

Post repository includes methods -

* findByStatus(PostStatus status, Pageable pageable): Returns posts based on PostStatus(DRAFT, PUBLISHED, ARCHIVED) for author only

* findByAuthor(String username): Lists posts of particular author based on username

*/

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByStatus(PostStatus status, Pageable pageable);

    List<Post> findByAuthor_Username(String username);

}
