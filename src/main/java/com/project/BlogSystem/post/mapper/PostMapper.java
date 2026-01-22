package com.project.BlogSystem.post.mapper;

import com.project.BlogSystem.post.dto.PostRequest;
import com.project.BlogSystem.post.dto.PostResponse;
import com.project.BlogSystem.post.model.entity.Post;
import com.project.BlogSystem.profile.mapper.ProfileMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "author", ignore = true)
    Post postRequestToPost(PostRequest postRequest);

    PostResponse postToPostResponse(Post post);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "author", ignore = true)
    void updatePostRequestToPost(PostRequest postRequest, @MappingTarget Post post);

}
