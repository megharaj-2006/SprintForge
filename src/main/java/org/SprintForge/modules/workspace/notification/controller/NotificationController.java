package org.SprintForge.modules.workspace.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.notification.entity.NotificationPreference;
import org.SprintForge.modules.workspace.notification.entity.TaskNotification;
import org.SprintForge.modules.workspace.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Notification Controller", description = "REST endpoints for in-app notifications and user preferences")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Get notifications for the logged in user")
    @GetMapping("/notifications")
    public ResponseEntity<List<TaskNotification>> getUserNotifications(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(actorId));
    }

    @Operation(summary = "Get unread notifications")
    @GetMapping("/notifications/unread")
    public ResponseEntity<List<TaskNotification>> getUnreadNotifications(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(actorId));
    }

    @Operation(summary = "Mark notification as read")
    @PatchMapping("/notifications/{id}/read")
    public ResponseEntity<TaskNotification> markAsRead(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(notificationService.markAsRead(id, actorId));
    }

    @Operation(summary = "Mark all notifications as read")
    @PatchMapping("/notifications/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        notificationService.markAllAsRead(actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete notification")
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        notificationService.deleteNotification(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get notification preferences")
    @GetMapping("/notification-preferences")
    public ResponseEntity<NotificationPreference> getPreferences(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(notificationService.getOrCreatePreference(actorId));
    }

    @Operation(summary = "Update notification preferences")
    @PatchMapping("/notification-preferences")
    public ResponseEntity<NotificationPreference> updatePreferences(
            @RequestBody NotificationPreference preferences,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(notificationService.updatePreference(actorId, preferences));
    }
}
