package com.project.BlogSystem.auth.mapper;

import com.project.BlogSystem.auth.dto.AuthResponse;
import com.project.BlogSystem.auth.dto.RegisterRequest;
import com.project.BlogSystem.auth.model.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AuthUser registerRequestToUser(RegisterRequest registerRequest);

    @Mapping(target = "token", ignore = true)
    AuthResponse userToAuthResponse(AuthUser user);
}
