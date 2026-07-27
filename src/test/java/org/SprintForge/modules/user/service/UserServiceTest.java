package org.SprintForge.modules.user.service;

import org.SprintForge.modules.activity.repository.ActivityLogRepository;
import org.SprintForge.modules.user.dto.*;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.exception.UserNotFoundException;
import org.SprintForge.modules.user.exception.UsernameAlreadyExistsException;
import org.SprintForge.modules.user.mapper.UserMapper;
import org.SprintForge.modules.user.repository.UserPreferenceRepository;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.user.validation.AvatarFileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private UserPreferenceService userPreferenceService;

    @Mock
    private ActivityLogRepository activityLogRepository;

    @Mock
    private AvatarFileValidator avatarFileValidator;

    @Spy
    private UserMapper userMapper = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserServiceImpl userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .fullName("John Doe")
                .bio("Software Engineer")
                .build();
        sampleUser.setId(1L);
    }

    @Test
    void getCurrentUserProfile_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        when(userPreferenceService.getUserPreferences(1L)).thenReturn(new UserPreferenceResponse());

        UserProfileResponse result = userService.getCurrentUserProfile(1L);

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void getCurrentUserProfile_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getCurrentUserProfile(99L));
    }

    @Test
    void getPublicUserProfile_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        PublicUserProfileResponse result = userService.getPublicUserProfile(1L);

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void updateUserProfile_Success() {
        UpdateProfileRequest request = UpdateProfileRequest.builder()
                .fullName("John Updated")
                .bio("Senior Software Engineer")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserProfileResponse result = userService.updateUserProfile(1L, request);

        assertNotNull(result);
        assertEquals("John Updated", result.getFullName());
        assertEquals("Senior Software Engineer", result.getBio());
    }

    @Test
    void updateUserProfile_UsernameAlreadyTaken() {
        UpdateProfileRequest request = UpdateProfileRequest.builder()
                .username("taken_username")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.existsByUsernameAndIdNot("taken_username", 1L)).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.updateUserProfile(1L, request));
    }

    @Test
    void checkUsernameAvailability_Available() {
        when(userRepository.existsByUsername("new_user")).thenReturn(false);

        UsernameCheckResponse response = userService.checkUsernameAvailability("new_user", null);

        assertTrue(response.isAvailable());
        assertEquals("Username is available", response.getMessage());
    }

    @Test
    void searchUsers_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.searchUsers("john", pageable))
                .thenReturn(new PageImpl<>(List.of(sampleUser), pageable, 1));

        UserSearchResponse response = userService.searchUsers("john", pageable);

        assertNotNull(response);
        assertEquals(1, response.getUsers().size());
        assertEquals("john_doe", response.getUsers().get(0).getUsername());
    }

    @Test
    void getUserPreferences_Success() {
        UserPreferenceResponse expected = UserPreferenceResponse.builder()
                .theme("DARK")
                .language("en")
                .timezone("UTC")
                .emailNotifications(true)
                .inAppNotifications(false)
                .build();

        when(userPreferenceService.getUserPreferences(1L)).thenReturn(expected);

        UserPreferenceResponse result = userService.getUserPreferences(1L);

        assertNotNull(result);
        assertEquals("DARK", result.getTheme());
        assertFalse(result.isInAppNotifications());
    }

    @Test
    void softDeleteAccount_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        userService.softDeleteAccount(1L);

        assertTrue(sampleUser.isDeleted());
        verify(userRepository, times(1)).save(sampleUser);
    }
}
