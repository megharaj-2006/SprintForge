package org.SprintForge.platform.communication.notification;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.dto.ApiResponse;
import org.SprintForge.platform.communication.dto.NotificationResponse;
import org.SprintForge.platform.communication.dto.UnreadCountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformNotificationController")
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class PlatformNotificationController {

    private final NotificationQueryService notificationQueryService;
    private final NotificationManagementService notificationManagementService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications(
            @RequestParam String userId,
            @RequestParam(defaultValue = "50") int limit) {
        List<NotificationResponse> list = notificationQueryService.getAllNotifications(userId, limit);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/unread")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUnreadNotifications(
            @RequestParam String userId,
            @RequestParam(defaultValue = "50") int limit) {
        List<NotificationResponse> list = notificationQueryService.getUnreadNotifications(userId, limit);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<UnreadCountResponse>> getUnreadCount(@RequestParam String userId) {
        UnreadCountResponse count = notificationQueryService.getUnreadCount(userId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<NotificationResponse>> markAsRead(@PathVariable Long id) {
        NotificationResponse res = notificationManagementService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<String>> markAllAsRead(@RequestParam String userId) {
        notificationManagementService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read"));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<ApiResponse<NotificationResponse>> archiveNotification(@PathVariable Long id) {
        NotificationResponse res = notificationManagementService.archiveNotification(id);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable Long id) {
        notificationManagementService.deleteNotification(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
