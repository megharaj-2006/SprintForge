package org.SprintForge.modules.user.service;

import org.SprintForge.modules.user.dto.UserPreferenceResponse;
import org.SprintForge.modules.user.entity.User;

public interface UserPreferenceService {

    UserPreferenceResponse getUserPreferences(Long userId);

    UserPreferenceResponse updateUserPreferences(Long userId, UserPreferenceResponse preferenceDto);

    UserPreferenceResponse updateTheme(Long userId, String theme);

    UserPreferenceResponse updateLanguage(Long userId, String language);

    UserPreferenceResponse updateTimezone(Long userId, String timezone);

    UserPreferenceResponse resetUserPreferencesToDefault(Long userId);

    UserPreferenceResponse getOrInitializePreferences(User user);
}
