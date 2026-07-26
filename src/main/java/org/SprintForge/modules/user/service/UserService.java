package org.SprintForge.modules.user.service;

import org.SprintForge.modules.user.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserProfileResponseDto getCurrentUserProfile(Long userId);

    PublicUserProfileDto getPublicUserProfile(Long userId);

    UserProfileResponseDto updateUserProfile(Long userId, UpdateProfileRequestDto request);

    UsernameCheckResponseDto checkUsernameAvailability(String username, Long currentUserId);

    UserSearchResponseDto searchUsers(String query, Pageable pageable);

    UserProfileResponseDto uploadProfileAvatar(Long userId, MultipartFile file);

    UserProfileResponseDto removeProfileAvatar(Long userId);

    UserPreferenceDto getUserPreferences(Long userId);

    UserPreferenceDto updateUserPreferences(Long userId, UserPreferenceDto preferenceDto);

    void softDeleteAccount(Long userId);

    UserProfileResponseDto restoreDeletedAccount(Long userId);

    UserStatisticsDto getUserStatistics(Long userId);

    List<UserActivityDto> getUserActivityFeed(Long userId, Pageable pageable);

    UserDataExportDto exportUserData(Long userId);

    void deactivateAccount(Long userId);

    UserProfileResponseDto reactivateAccount(Long userId);
}