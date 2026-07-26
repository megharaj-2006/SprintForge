package org.SprintForge.modules.auth.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.auth.dto.SignupRequest;
import org.SprintForge.modules.user.entity.User;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "suspended", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    @Mapping(target = "bio", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "lastActiveAt", ignore = true)
    User toUser(SignupRequest signupRequest);
}
