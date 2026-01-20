package com.project.BlogSystem.profile.mapper;

import com.project.BlogSystem.profile.dto.ProfileResponse;
import com.project.BlogSystem.profile.dto.UpdateProfileRequest;
import com.project.BlogSystem.profile.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/*

Mapstruct mapper for the profile 
To return Profile response 
To update profile request

*/

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(source = "user.username", target = "username")
    ProfileResponse profileToProfileResponse(Profile profile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateProfileRequest(UpdateProfileRequest updateProfileRequest, @MappingTarget Profile profile);
}
