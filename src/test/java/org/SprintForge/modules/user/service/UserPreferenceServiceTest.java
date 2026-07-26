package org.SprintForge.modules.user.service;

import org.SprintForge.modules.activity.repository.ActivityLogRepository;
import org.SprintForge.modules.user.dto.UserPreferenceDto;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.entity.UserPreference;
import org.SprintForge.modules.user.mapper.UserMapper;
import org.SprintForge.modules.user.repository.UserPreferenceRepository;
import org.SprintForge.modules.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPreferenceServiceTest {

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivityLogRepository activityLogRepository;

    @Spy
    private UserMapper userMapper = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserPreferenceServiceImpl userPreferenceService;

    private User sampleUser;
    private UserPreference samplePreference;

    @BeforeEach
    void setUp() {
        sampleUser = User.builder().username("test_user").email("test@example.com").build();
        sampleUser.setId(1L);

        samplePreference = UserPreference.builder()
                .user(sampleUser)
                .theme("LIGHT")
                .language("en")
                .timezone("UTC")
                .emailNotifications(true)
                .inAppNotifications(true)
                .build();
    }

    @Test
    void getUserPreferences_Existing() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userPreferenceRepository.findByUserId(1L)).thenReturn(Optional.of(samplePreference));

        UserPreferenceDto dto = userPreferenceService.getUserPreferences(1L);

        assertNotNull(dto);
        assertEquals("LIGHT", dto.getTheme());
        assertEquals("en", dto.getLanguage());
    }

    @Test
    void updateTheme_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userPreferenceRepository.findByUserId(1L)).thenReturn(Optional.of(samplePreference));
        when(userPreferenceRepository.save(any(UserPreference.class))).thenAnswer(i -> i.getArgument(0));

        UserPreferenceDto updated = userPreferenceService.updateTheme(1L, "DARK");

        assertNotNull(updated);
        assertEquals("DARK", updated.getTheme());
    }

    @Test
    void resetPreferences_Success() {
        samplePreference.setTheme("DARK");
        samplePreference.setLanguage("es");

        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userPreferenceRepository.findByUserId(1L)).thenReturn(Optional.of(samplePreference));
        when(userPreferenceRepository.save(any(UserPreference.class))).thenAnswer(i -> i.getArgument(0));

        UserPreferenceDto reset = userPreferenceService.resetUserPreferencesToDefault(1L);

        assertNotNull(reset);
        assertEquals("LIGHT", reset.getTheme());
        assertEquals("en", reset.getLanguage());
    }
}
