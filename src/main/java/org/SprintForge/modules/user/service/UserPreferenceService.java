package org.SprintForge.modules.user.service;

import org.SprintForge.modules.user.dto.UserPreferenceDto;
import org.SprintForge.modules.user.entity.User;

public interface UserPreferenceService {

    UserPreferenceDto getUserPreferences(Long userId);

    UserPreferenceDto updateUserPreferences(Long userId, UserPreferenceDto preferenceDto);

    UserPreferenceDto updateTheme(Long userId, String theme);

    UserPreferenceDto updateLanguage(Long userId, String language);

    UserPreferenceDto updateTimezone(Long userId, String timezone);

    UserPreferenceDto resetUserPreferencesToDefault(Long userId);

    UserPreferenceDto getOrInitializePreferences(User user);
}
