
package com.project.BlogSystem.post.service.interfaces;

import com.project.BlogSystem.post.dto.PostRequest;
import com.project.BlogSystem.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostResponse createPost(PostRequest postRequest, MultipartFile file);

    PostResponse updatePost(Long postId, PostRequest postRequest, MultipartFile file);

    void deletePost(Long postId);

    Page<PostResponse> getPostsByUser(String name, Pageable pageable);
}
