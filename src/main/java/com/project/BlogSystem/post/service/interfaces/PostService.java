
package com.project.BlogSystem.post.service.interfaces;

import com.project.BlogSystem.post.dto.PostRequest;
import com.project.BlogSystem.post.dto.PostResponse;
import com.project.BlogSystem.post.model.enums.PostStatus;
import com.project.BlogSystem.auth.security.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse createPost(PostRequest postRequest, AuthenticatedUser currentUser);

    PostResponse updatePost(Long postId, PostRequest postRequest, AuthenticatedUser currentUser);

    void deletePost(Long postId, AuthenticatedUser currentUser);

    Page<PostResponse> getPostsByUser(String name, Pageable pageable);

    PostResponse changePostStatus(Long postId, PostStatus newStatus, AuthenticatedUser currentUser);
}

