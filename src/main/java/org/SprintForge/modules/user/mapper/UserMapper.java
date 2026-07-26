package org.SprintForge.modules.user.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.user.dto.*;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.entity.UserPreference;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {

    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "preferences", ignore = true)
    @Mapping(target = "stats", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    UserProfileResponseDto toProfileResponseDto(User user);

    default UserProfileResponseDto toProfileResponseDto(User user, UserPreferenceDto preferences, UserStatisticsDto stats) {
        if (user == null) {
            return null;
        }
        UserProfileResponseDto dto = toProfileResponseDto(user);
        dto.setPreferences(preferences);
        dto.setStats(stats);
        
        List<String> rolesList = user.getRole() != null
                ? List.of(user.getRole().getName())
                : Collections.emptyList();
        dto.setRoles(rolesList);

        String displayName = user.getDisplayName() != null && !user.getDisplayName().isBlank()
                ? user.getDisplayName()
                : (user.getFullName() != null && !user.getFullName().isBlank() ? user.getFullName() : user.getUsername());
        dto.setDisplayName(displayName);
        
        return dto;
    }

    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "displayName", ignore = true)
    PublicUserProfileDto toPublicProfileDto(User user);

    @AfterMapping
    default void setPublicDisplayName(User user, @MappingTarget PublicUserProfileDto builder) {
        if (user != null) {
            String displayName = user.getDisplayName() != null && !user.getDisplayName().isBlank()
                    ? user.getDisplayName()
                    : (user.getFullName() != null && !user.getFullName().isBlank() ? user.getFullName() : user.getUsername());
            builder.setDisplayName(displayName);
        }
    }

    @AfterMapping
    default void setProfileDisplayName(User user, @MappingTarget UserProfileResponseDto builder) {
        if (user != null) {
            String displayName = user.getDisplayName() != null && !user.getDisplayName().isBlank()
                    ? user.getDisplayName()
                    : (user.getFullName() != null && !user.getFullName().isBlank() ? user.getFullName() : user.getUsername());
            builder.setDisplayName(displayName);

            List<String> rolesList = user.getRole() != null
                    ? List.of(user.getRole().getName())
                    : Collections.emptyList();
            builder.setRoles(rolesList);
        }
    }

    UserPreferenceDto toPreferenceDto(UserPreference preference);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "suspended", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "lastActiveAt", ignore = true)
    void updateEntity(UpdateProfileRequestDto dto, @MappingTarget User user);
}
