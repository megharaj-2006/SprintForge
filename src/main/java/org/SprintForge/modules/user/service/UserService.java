package org.SprintForge.modules.user.service;

import org.SprintForge.modules.user.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserProfileResponse getCurrentUserProfile(Long userId);

    PublicUserProfileResponse getPublicUserProfile(Long userId);

    UserProfileResponse updateUserProfile(Long userId, UpdateProfileRequest request);

    UsernameCheckResponse checkUsernameAvailability(String username, Long currentUserId);

    UserSearchResponse searchUsers(String query, Pageable pageable);

    UserProfileResponse uploadProfileAvatar(Long userId, MultipartFile file);

    UserProfileResponse removeProfileAvatar(Long userId);

    UserPreferenceResponse getUserPreferences(Long userId);

    UserPreferenceResponse updateUserPreferences(Long userId, UserPreferenceResponse preferenceDto);

    void softDeleteAccount(Long userId);

    UserProfileResponse restoreDeletedAccount(Long userId);

    UserStatisticsResponse getUserStatistics(Long userId);

    List<UserActivityResponse> getUserActivityFeed(Long userId, Pageable pageable);

    UserDataExportResponse exportUserData(Long userId);

    void deactivateAccount(Long userId);

    UserProfileResponse reactivateAccount(Long userId);
}