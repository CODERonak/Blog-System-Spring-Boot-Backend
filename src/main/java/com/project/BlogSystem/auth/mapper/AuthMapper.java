package com.project.BlogSystem.auth.mapper;

import com.project.BlogSystem.auth.dto.request.RegisterRequest;
import com.project.BlogSystem.auth.dto.response.AuthResponse;
import com.project.BlogSystem.auth.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "id", ignore = true)
    User registerRequestToUser(RegisterRequest registerRequest);

    @Mapping(target = "token", ignore = true)
    AuthResponse userToAuthResponse(User user);
}
