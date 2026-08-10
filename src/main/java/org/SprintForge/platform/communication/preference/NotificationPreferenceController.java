package org.SprintForge.platform.communication.preference;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.dto.ApiResponse;
import org.SprintForge.platform.communication.dto.NotificationPreferenceResponse;
import org.SprintForge.platform.communication.dto.NotificationPreferenceUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformNotificationPreferenceController")
@RequestMapping("/notification-preferences")
@RequiredArgsConstructor
public class NotificationPreferenceController {

    private final NotificationPreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationPreferenceResponse>>> getUserPreferences(@RequestParam String userId) {
        List<NotificationPreferenceResponse> preferences = preferenceService.getUserPreferences(userId);
        return ResponseEntity.ok(ApiResponse.success(preferences));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<NotificationPreferenceResponse>> updatePreference(
            @RequestBody NotificationPreferenceUpdateRequest request) {
        NotificationPreferenceResponse updated = preferenceService.updatePreference(request);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }
}
