package com.project.BlogSystem.post.controller;

import com.project.BlogSystem.post.dto.*;
import com.project.BlogSystem.post.service.interfaces.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

   private final PostService postService;
   private final ObjectMapper objectMapper;

   @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<PostResponse> createPost(
           @Valid @RequestPart("post") String postRequestString,
           @RequestPart(value = "image", required = false) MultipartFile image) throws JsonProcessingException {
       PostRequest postRequest = objectMapper.readValue(postRequestString, PostRequest.class);
       return new ResponseEntity<>(postService.createPost(postRequest, image), HttpStatus.CREATED);
   }

   @PutMapping(value = "/update/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<PostResponse> updatePost(
           @PathVariable Long postId,
           @Valid @RequestPart("post") String postRequestString,
           @RequestPart(value = "image", required = false) MultipartFile image) throws JsonProcessingException {
       PostRequest postRequest = objectMapper.readValue(postRequestString, PostRequest.class);
       return new ResponseEntity<>(postService.updatePost(postId, postRequest, image), HttpStatus.OK);
   }

   @DeleteMapping("/delete/{postId}")
   public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
       postService.deletePost(postId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/user/{username}")
   public ResponseEntity<Page<PostResponse>> getPostsByUser(
           @PathVariable String username,
           Pageable pageable) {
       return new ResponseEntity<>(postService.getPostsByUser(username, pageable), HttpStatus.OK);
   }
}

