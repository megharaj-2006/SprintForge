package org.SprintForge.modules.workspace.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.notification.entity.NotificationPreference;
import org.SprintForge.modules.workspace.notification.entity.TaskNotification;
import org.SprintForge.modules.workspace.notification.repository.NotificationPreferenceRepository;
import org.SprintForge.modules.workspace.notification.repository.TaskNotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TaskNotificationRepository notificationRepository;
    private final NotificationPreferenceRepository preferenceRepository;

    @Transactional
    public TaskNotification createNotification(Long recipientUserId, Long actorId, String type, String title, String message, Long taskId, String actionUrl) {
        NotificationPreference pref = getOrCreatePreference(recipientUserId);
        if (Boolean.TRUE.equals(pref.getDoNotDisturb()) && !"MENTION".equalsIgnoreCase(type)) {
            log.info("Notification skipped for user {} due to Do Not Disturb", recipientUserId);
            return null;
        }

        TaskNotification notification = new TaskNotification();
        notification.setUserId(recipientUserId);
        notification.setActorId(actorId);
        notification.setType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setTaskId(taskId);
        notification.setActionUrl(actionUrl);
        notification.setIsRead(false);

        return notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<TaskNotification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<TaskNotification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalseAndIsDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public TaskNotification markAsRead(Long notificationId, Long userId) {
        TaskNotification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + notificationId));
        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<TaskNotification> unread = notificationRepository.findByUserIdAndIsReadFalseAndIsDeletedFalseOrderByCreatedAtDesc(userId);
        for (TaskNotification n : unread) {
            n.setIsRead(true);
            n.setReadAt(LocalDateTime.now());
        }
        notificationRepository.saveAll(unread);
    }

    @Transactional
    public void deleteNotification(Long notificationId, Long userId) {
        TaskNotification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + notificationId));
        notification.setDeleted(true);
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public NotificationPreference getOrCreatePreference(Long userId) {
        return preferenceRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseGet(() -> {
                    NotificationPreference p = new NotificationPreference();
                    p.setUserId(userId);
                    return preferenceRepository.save(p);
                });
    }

    @Transactional
    public NotificationPreference updatePreference(Long userId, NotificationPreference updated) {
        NotificationPreference existing = getOrCreatePreference(userId);
        if (updated.getEnableInApp() != null) existing.setEnableInApp(updated.getEnableInApp());
        if (updated.getEnableEmail() != null) existing.setEnableEmail(updated.getEnableEmail());
        if (updated.getEnableMentions() != null) existing.setEnableMentions(updated.getEnableMentions());
        if (updated.getEnableComments() != null) existing.setEnableComments(updated.getEnableComments());
        if (updated.getEnableAssignments() != null) existing.setEnableAssignments(updated.getEnableAssignments());
        if (updated.getEnableWatchers() != null) existing.setEnableWatchers(updated.getEnableWatchers());
        if (updated.getDoNotDisturb() != null) existing.setDoNotDisturb(updated.getDoNotDisturb());
        return preferenceRepository.save(existing);
    }
}
