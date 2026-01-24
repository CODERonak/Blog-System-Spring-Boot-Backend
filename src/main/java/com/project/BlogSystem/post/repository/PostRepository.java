package com.project.BlogSystem.post.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.BlogSystem.post.model.entity.Post;
import com.project.BlogSystem.profile.model.Profile;

/*

Post repository includes methods -

* findByAuthorProfile authorProfile, Pageable pageable): Lists posts of particular author based on username

*/

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAuthor(Profile authorProfile, Pageable pageable);

}
