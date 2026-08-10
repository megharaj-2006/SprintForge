package org.SprintForge.platform.communication.preference;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.communication.dto.NotificationPreferenceResponse;
import org.SprintForge.platform.communication.dto.NotificationPreferenceUpdateRequest;
import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("platformNotificationPreferenceService")
@RequiredArgsConstructor
public class NotificationPreferenceService {

    private final NotificationPreferenceRepository preferenceRepository;

    @Transactional(readOnly = true)
    public List<NotificationPreferenceResponse> getUserPreferences(String userId) {
        return preferenceRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public NotificationPreferenceResponse updatePreference(NotificationPreferenceUpdateRequest request) {
        NotificationChannel channel = request.getChannel() != null ? request.getChannel() : NotificationChannel.IN_APP;
        NotificationType type = request.getNotificationType() != null ? request.getNotificationType() : NotificationType.SYSTEM;

        NotificationPreference preference = preferenceRepository
                .findByUserIdAndWorkspaceIdAndChannelAndNotificationType(
                        request.getUserId(), request.getWorkspaceId(), channel, type)
                .orElseGet(() -> NotificationPreference.builder()
                        .userId(request.getUserId())
                        .workspaceId(request.getWorkspaceId())
                        .channel(channel)
                        .notificationType(type)
                        .build());

        if (request.getEnabled() != null) {
            preference.setEnabled(request.getEnabled());
        }
        if (request.getMuteUntil() != null) {
            preference.setMuteUntil(request.getMuteUntil());
        }
        if (request.getDigestFrequency() != null) {
            preference.setDigestFrequency(request.getDigestFrequency());
        }

        preference = preferenceRepository.save(preference);
        return mapToResponse(preference);
    }

    private NotificationPreferenceResponse mapToResponse(NotificationPreference pref) {
        return NotificationPreferenceResponse.builder()
                .id(pref.getId())
                .userId(pref.getUserId())
                .workspaceId(pref.getWorkspaceId())
                .channel(pref.getChannel())
                .notificationType(pref.getNotificationType())
                .enabled(pref.isEnabled())
                .muteUntil(pref.getMuteUntil())
                .digestFrequency(pref.getDigestFrequency())
                .build();
    }
}
