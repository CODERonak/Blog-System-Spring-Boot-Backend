package com.project.BlogSystem.post.service.impl;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.auth.security.AuthenticatedUser;
import com.project.BlogSystem.post.dto.*;
import com.project.BlogSystem.post.exception.*;
import com.project.BlogSystem.post.mapper.PostMapper;
import com.project.BlogSystem.post.model.entity.Post;
import com.project.BlogSystem.post.repository.PostRepository;
import com.project.BlogSystem.post.service.interfaces.PostService;
import com.project.BlogSystem.profile.exception.ProfileNotFoundException;
import com.project.BlogSystem.profile.model.Profile;
import com.project.BlogSystem.profile.repository.ProfileRepository;
import com.project.BlogSystem.storage.interfaces.GCPStorageService;
import com.project.BlogSystem.auth.model.enums.Role;

import lombok.AllArgsConstructor;

/*

PostServiceImpl implements PostService, which includes methods:
* PostResponse createPost(PostRequest postRequest, MultipartFile file)
* PostResponse updatePost(Long postId, PostRequest postRequest, MultipartFile file)
* void deletePost(Long postId)
* Page<PostResponse> getPostsByUser(String username, Pageable pageable)

*/

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

   private final PostRepository postRepository;
   private final PostMapper postMapper;
   private final GCPStorageService gcpStorageService;
   private final AuthenticatedUser authenticatedUser;
   private final ProfileRepository profileRepository;

   @Override
   public PostResponse createPost(PostRequest postRequest, MultipartFile file) {

       AuthUser author = authenticatedUser.getLoggedInUser();

       if (author.getRole() != Role.AUTHOR)
           throw new UnauthorizedActionException("Only users with the AUTHOR role can create posts.");

       try {
           String filename = gcpStorageService.uploadFile(file);

           String fileUrl = gcpStorageService.getFileUrl(filename);

           Post post = postMapper.postRequestToPost(postRequest);
           post.setImageUrl(fileUrl);

           Profile authorProfile = profileRepository.findByUser_Username(author.getUsername())
                   .orElseThrow(
                           () -> new ProfileNotFoundException("Profile not found for user: " + author.getUsername()));

           post.setAuthor(authorProfile);

           Post savedPost = postRepository.save(post);
           return postMapper.postToPostResponse(savedPost);

       } catch (IOException e) {
           throw new PostCreationException("Error processing file upload during post creation.");
       }
   }

   @Override
   public PostResponse updatePost(Long postId, PostRequest postRequest, MultipartFile file) {
       Post existingPost = postRepository.findById(postId)
               .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + "postId"));

       AuthUser currentUser = authenticatedUser.getLoggedInUser();

       if (!existingPost.getAuthor().getUser().equals(currentUser) && currentUser.getRole() != Role.AUTHOR)
           throw new UnauthorizedActionException("You are not authorized to update this post.");

       try {

           gcpStorageService.deleteFile(existingPost.getImageUrl());

           String newFileName = gcpStorageService.uploadFile(file);
           String newFileUrl = gcpStorageService.getFileUrl(newFileName);

           existingPost.setTitle(postRequest.getTitle());
           existingPost.setContent(postRequest.getContent());
           existingPost.setImageUrl(newFileUrl);

           Post updatedPost = postRepository.save(existingPost);
           return postMapper.postToPostResponse(updatedPost);
       } catch (IOException e) {
           throw new PostCreationException("Error processing file upload during post update.");
       }
   }

   @Override
   public void deletePost(Long postId) {

       AuthUser currentUser = authenticatedUser.getLoggedInUser();

       Post post = postRepository.findById(postId)
               .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + "postId"));

       if (!post.getAuthor().getUser().equals(currentUser) && currentUser.getRole() != Role.AUTHOR)
           throw new UnauthorizedActionException("You are not authorized to delete this post.");

       postRepository.delete(post);

       try {
           gcpStorageService.deleteFile(post.getImageUrl());
       } catch (IOException e) {
           throw new PostCreationException("Failed to delete the post");
       }
   }

   @Override
   public Page<PostResponse> getPostsByUser(String username, Pageable pageable) {
       Profile authorProfile = profileRepository.findByUser_Username(username)
               .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user: " + username));
       Page<Post> posts = postRepository.findByAuthor(authorProfile, pageable);

       return posts.map(postMapper::postToPostResponse);
   }

}
