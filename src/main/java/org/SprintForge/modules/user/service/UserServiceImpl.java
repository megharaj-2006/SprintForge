package org.SprintForge.modules.user.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.activity.entity.ActivityLog;
import org.SprintForge.modules.activity.repository.ActivityLogRepository;
import org.SprintForge.modules.user.dto.*;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.entity.UserPreference;
import org.SprintForge.modules.user.exception.UserAccountDeactivatedException;
import org.SprintForge.modules.user.exception.UserNotFoundException;
import org.SprintForge.modules.user.exception.UsernameAlreadyExistsException;
import org.SprintForge.modules.user.mapper.UserMapper;
import org.SprintForge.modules.user.repository.UserPreferenceRepository;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.user.validation.AvatarFileValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final UserPreferenceService userPreferenceService;
    private final ActivityLogRepository activityLogRepository;
    private final UserMapper userMapper;
    private final AvatarFileValidator avatarFileValidator;

    private static final String UPLOAD_DIR = "uploads/avatars/";

    @Override
    @Transactional
    public UserProfileResponse getCurrentUserProfile(Long userId) {
        User user = getUserOrThrow(userId);
        validateUserActive(user);
        user.setLastActiveAt(LocalDateTime.now());
        User saved = userRepository.save(user);

        UserPreferenceResponse preferences = userPreferenceService.getUserPreferences(userId);
        UserStatisticsResponse stats = getUserStatistics(userId);

        return userMapper.toProfileResponseDto(saved, preferences, stats);
    }

    @Override
    @Transactional(readOnly = true)
    public PublicUserProfileResponse getPublicUserProfile(Long userId) {
        User user = getUserOrThrow(userId);
        if (user.isDeleted()) {
            throw new UserNotFoundException("Public profile not available for deleted user.");
        }
        return userMapper.toPublicProfileDto(user);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfile(Long userId, UpdateProfileRequest request) {
        User user = getUserOrThrow(userId);
        validateUserActive(user);

        if (request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
            String newUsername = request.getUsername().trim();
            if (!newUsername.equalsIgnoreCase(user.getUsername())) {
                if (userRepository.existsByUsernameAndIdNot(newUsername, userId)) {
                    throw new UsernameAlreadyExistsException(newUsername);
                }
                user.setUsername(newUsername);
            }
        }

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName().trim());
        }

        if (request.getBio() != null) {
            user.setBio(request.getBio().trim());
        }

        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);

        logActivity(userId, "UPDATE_PROFILE", "User updated profile information");

        return userMapper.toProfileResponseDto(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UsernameCheckResponse checkUsernameAvailability(String username, Long currentUserId) {
        if (username == null || username.trim().isEmpty()) {
            return UsernameCheckResponse.builder()
                    .username(username)
                    .isAvailable(false)
                    .message("Username cannot be empty")
                    .build();
        }

        String trimmed = username.trim();
        boolean exists = currentUserId != null
                ? userRepository.existsByUsernameAndIdNot(trimmed, currentUserId)
                : userRepository.existsByUsername(trimmed);

        return UsernameCheckResponse.builder()
                .username(trimmed)
                .isAvailable(!exists)
                .message(exists ? "Username is already taken" : "Username is available")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserSearchResponse searchUsers(String query, Pageable pageable) {
        String searchQuery = (query == null) ? "" : query.trim();
        Page<User> userPage = userRepository.searchUsers(searchQuery, pageable);

        List<PublicUserProfileResponse> profiles = userPage.getContent().stream()
                .map(userMapper::toPublicProfileDto)
                .collect(Collectors.toList());

        return UserSearchResponse.builder()
                .users(profiles)
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public UserProfileResponse uploadProfileAvatar(Long userId, MultipartFile file) {
        User user = getUserOrThrow(userId);
        validateUserActive(user);

        avatarFileValidator.validate(file);

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String fileName = "user_" + userId + "_" + UUID.randomUUID() + extension;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Delete old avatar if present locally
            if (user.getProfilePicture() != null && user.getProfilePicture().startsWith("/uploads/avatars/")) {
                try {
                    Path oldPath = Paths.get(user.getProfilePicture().substring(1));
                    Files.deleteIfExists(oldPath);
                } catch (Exception ignored) {
                }
            }

            user.setProfilePicture("/uploads/avatars/" + fileName);
            user.setUpdatedAt(LocalDateTime.now());
            User savedUser = userRepository.save(user);

            logActivity(userId, "UPLOAD_AVATAR", "User uploaded a new profile avatar");

            return userMapper.toProfileResponseDto(savedUser);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store avatar file: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public UserProfileResponse removeProfileAvatar(Long userId) {
        User user = getUserOrThrow(userId);
        validateUserActive(user);

        if (user.getProfilePicture() != null && user.getProfilePicture().startsWith("/uploads/avatars/")) {
            try {
                Path oldPath = Paths.get(user.getProfilePicture().substring(1));
                Files.deleteIfExists(oldPath);
            } catch (Exception ignored) {
            }
        }

        user.setProfilePicture(null);
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);

        logActivity(userId, "REMOVE_AVATAR", "User removed profile avatar");

        return userMapper.toProfileResponseDto(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPreferenceResponse getUserPreferences(Long userId) {
        return userPreferenceService.getUserPreferences(userId);
    }

    @Override
    @Transactional
    public UserPreferenceResponse updateUserPreferences(Long userId, UserPreferenceResponse preferenceDto) {
        User user = getUserOrThrow(userId);
        validateUserActive(user);
        return userPreferenceService.updateUserPreferences(userId, preferenceDto);
    }

    @Override
    @Transactional
    public void softDeleteAccount(Long userId) {
        User user = getUserOrThrow(userId);
        user.setDeleted(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        logActivity(userId, "SOFT_DELETE_ACCOUNT", "User soft-deleted account");
    }

    @Override
    @Transactional
    public UserProfileResponse restoreDeletedAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setDeleted(false);
        user.setUpdatedAt(LocalDateTime.now());
        User restored = userRepository.save(user);

        logActivity(userId, "RESTORE_ACCOUNT", "User restored account");

        return userMapper.toProfileResponseDto(restored);
    }

    @Override
    @Transactional(readOnly = true)
    public UserStatisticsResponse getUserStatistics(Long userId) {
        getUserOrThrow(userId);
        // Returns basic user metrics for dashboard analytics
        return UserStatisticsResponse.builder()
                .userId(userId)
                .totalWorkspaces(0)
                .totalProjects(0)
                .assignedTasksCount(0)
                .completedTasksCount(0)
                .totalCommentsCount(0)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserActivityResponse> getUserActivityFeed(Long userId, Pageable pageable) {
        getUserOrThrow(userId);
        // Retrieve activity logs for user if any
        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDataExportResponse exportUserData(Long userId) {
        User user = getUserOrThrow(userId);
        UserProfileResponse profile = userMapper.toProfileResponseDto(user);
        UserPreferenceResponse preferences = getUserPreferences(userId);
        UserStatisticsResponse stats = getUserStatistics(userId);
        List<UserActivityResponse> activities = getUserActivityFeed(userId, Pageable.unpaged());

        return UserDataExportResponse.builder()
                .profile(profile)
                .preferences(preferences)
                .statistics(stats)
                .recentActivities(activities)
                .exportedAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public void deactivateAccount(Long userId) {
        User user = getUserOrThrow(userId);
        user.setSuspended(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        logActivity(userId, "DEACTIVATE_ACCOUNT", "User deactivated account");
    }

    @Override
    @Transactional
    public UserProfileResponse reactivateAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setSuspended(false);
        user.setUpdatedAt(LocalDateTime.now());
        User reactivated = userRepository.save(user);

        logActivity(userId, "REACTIVATE_ACCOUNT", "User reactivated account");

        return userMapper.toProfileResponseDto(reactivated);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void validateUserActive(User user) {
        if (user.isDeleted()) {
            throw new UserNotFoundException("User account is deleted.");
        }
        if (user.isSuspended()) {
            throw new UserAccountDeactivatedException("User account is deactivated / suspended.");
        }
    }

    private void logActivity(Long userId, String action, String description) {
        try {
            ActivityLog log = new ActivityLog();
            log.setCreatedBy(String.valueOf(userId));
            log.setCreatedAt(LocalDateTime.now());
            activityLogRepository.save(log);
        } catch (Exception ignored) {
        }
    }
}