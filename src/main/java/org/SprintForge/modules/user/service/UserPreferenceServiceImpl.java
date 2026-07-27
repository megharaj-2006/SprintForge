package org.SprintForge.modules.user.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.activity.entity.ActivityLog;
import org.SprintForge.modules.activity.repository.ActivityLogRepository;
import org.SprintForge.modules.user.dto.UserPreferenceResponse;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.entity.UserPreference;
import org.SprintForge.modules.user.exception.UserNotFoundException;
import org.SprintForge.modules.user.mapper.UserMapper;
import org.SprintForge.modules.user.repository.UserPreferenceRepository;
import org.SprintForge.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserRepository userRepository;
    private final ActivityLogRepository activityLogRepository;
    private final UserMapper userMapper;

    private static final String DEFAULT_THEME = "LIGHT";
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String DEFAULT_TIMEZONE = "UTC";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    @Override
    @Transactional(readOnly = true)
    public UserPreferenceResponse getUserPreferences(Long userId) {
        User user = getUserOrThrow(userId);
        UserPreference preference = userPreferenceRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultPreferencesEntity(user));

        return userMapper.toPreferenceDto(preference);
    }

    @Override
    @Transactional
    public UserPreferenceResponse updateUserPreferences(Long userId, UserPreferenceResponse preferenceDto) {
        User user = getUserOrThrow(userId);

        UserPreference preference = userPreferenceRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultPreferencesEntity(user));

        if (preferenceDto.getTheme() != null && !preferenceDto.getTheme().isBlank()) {
            preference.setTheme(preferenceDto.getTheme().trim().toUpperCase());
        }

        if (preferenceDto.getLanguage() != null && !preferenceDto.getLanguage().isBlank()) {
            preference.setLanguage(preferenceDto.getLanguage().trim().toLowerCase());
        }

        if (preferenceDto.getTimezone() != null && !preferenceDto.getTimezone().isBlank()) {
            preference.setTimezone(preferenceDto.getTimezone().trim());
        }

        if (preferenceDto.getDateFormat() != null && !preferenceDto.getDateFormat().isBlank()) {
            preference.setDateFormat(preferenceDto.getDateFormat().trim());
        }

        preference.setEmailNotifications(preferenceDto.isEmailNotifications());
        preference.setPushNotifications(preferenceDto.isPushNotifications());
        preference.setInAppNotifications(preferenceDto.isInAppNotifications());
        preference.setTaskReminderEnabled(preferenceDto.isTaskReminderEnabled());
        preference.setUpdatedAt(LocalDateTime.now());

        UserPreference saved = userPreferenceRepository.save(preference);
        logActivity(userId, "UPDATE_PREFERENCES", "Updated user preference settings");

        return userMapper.toPreferenceDto(saved);
    }

    @Override
    @Transactional
    public UserPreferenceResponse updateTheme(Long userId, String theme) {
        if (theme == null || theme.isBlank()) {
            throw new IllegalArgumentException("Theme value cannot be blank");
        }
        User user = getUserOrThrow(userId);
        UserPreference preference = getOrCreatePreference(user);

        preference.setTheme(theme.trim().toUpperCase());
        preference.setUpdatedAt(LocalDateTime.now());

        UserPreference saved = userPreferenceRepository.save(preference);
        logActivity(userId, "UPDATE_THEME", "Updated preference theme to " + theme);

        return userMapper.toPreferenceDto(saved);
    }

    @Override
    @Transactional
    public UserPreferenceResponse updateLanguage(Long userId, String language) {
        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("Language value cannot be blank");
        }
        User user = getUserOrThrow(userId);
        UserPreference preference = getOrCreatePreference(user);

        preference.setLanguage(language.trim().toLowerCase());
        preference.setUpdatedAt(LocalDateTime.now());

        UserPreference saved = userPreferenceRepository.save(preference);
        logActivity(userId, "UPDATE_LANGUAGE", "Updated preference language to " + language);

        return userMapper.toPreferenceDto(saved);
    }

    @Override
    @Transactional
    public UserPreferenceResponse updateTimezone(Long userId, String timezone) {
        if (timezone == null || timezone.isBlank()) {
            throw new IllegalArgumentException("Timezone value cannot be blank");
        }
        User user = getUserOrThrow(userId);
        UserPreference preference = getOrCreatePreference(user);

        preference.setTimezone(timezone.trim());
        preference.setUpdatedAt(LocalDateTime.now());

        UserPreference saved = userPreferenceRepository.save(preference);
        logActivity(userId, "UPDATE_TIMEZONE", "Updated preference timezone to " + timezone);

        return userMapper.toPreferenceDto(saved);
    }

    @Override
    @Transactional
    public UserPreferenceResponse resetUserPreferencesToDefault(Long userId) {
        User user = getUserOrThrow(userId);
        UserPreference preference = getOrCreatePreference(user);

        preference.setTheme(DEFAULT_THEME);
        preference.setLanguage(DEFAULT_LANGUAGE);
        preference.setTimezone(DEFAULT_TIMEZONE);
        preference.setDateFormat(DEFAULT_DATE_FORMAT);
        preference.setEmailNotifications(true);
        preference.setPushNotifications(true);
        preference.setInAppNotifications(true);
        preference.setTaskReminderEnabled(true);
        preference.setUpdatedAt(LocalDateTime.now());

        UserPreference saved = userPreferenceRepository.save(preference);
        logActivity(userId, "RESET_PREFERENCES", "Reset user preferences to defaults");

        return userMapper.toPreferenceDto(saved);
    }

    @Override
    @Transactional
    public UserPreferenceResponse getOrInitializePreferences(User user) {
        UserPreference preference = getOrCreatePreference(user);
        return userMapper.toPreferenceDto(preference);
    }

    private UserPreference getOrCreatePreference(User user) {
        return userPreferenceRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    UserPreference defaultPref = createDefaultPreferencesEntity(user);
                    return userPreferenceRepository.save(defaultPref);
                });
    }

    private UserPreference createDefaultPreferencesEntity(User user) {
        return UserPreference.builder()
                .user(user)
                .theme(DEFAULT_THEME)
                .language(DEFAULT_LANGUAGE)
                .timezone(DEFAULT_TIMEZONE)
                .dateFormat(DEFAULT_DATE_FORMAT)
                .emailNotifications(true)
                .pushNotifications(true)
                .inAppNotifications(true)
                .taskReminderEnabled(true)
                .build();
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
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
