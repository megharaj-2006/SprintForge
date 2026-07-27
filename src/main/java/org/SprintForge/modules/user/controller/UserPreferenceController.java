package org.SprintForge.modules.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.user.dto.UserPreferenceResponse;
import org.SprintForge.modules.user.service.UserPreferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/me/preferences")
@RequiredArgsConstructor
@Tag(name = "User Preferences Controller", description = "APIs for viewing and modifying theme, language, timezone, and notification settings")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @Operation(summary = "Get user preferences", description = "Fetches preferences or returns system default settings.")
    @ApiResponse(responseCode = "200", description = "Preferences successfully retrieved")
    @GetMapping
    public ResponseEntity<UserPreferenceResponse> getUserPreferences(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId) {
        UserPreferenceResponse preferences = userPreferenceService.getUserPreferences(currentUserId);
        return ResponseEntity.ok(preferences);
    }

    @Operation(summary = "Update user preferences", description = "Updates full user preferences object.")
    @ApiResponse(responseCode = "200", description = "Preferences updated successfully")
    @PutMapping
    public ResponseEntity<UserPreferenceResponse> updateUserPreferences(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId,
            @Valid @RequestBody UserPreferenceResponse preferenceDto) {
        UserPreferenceResponse updatedPreferences = userPreferenceService.updateUserPreferences(currentUserId, preferenceDto);
        return ResponseEntity.ok(updatedPreferences);
    }

    @Operation(summary = "Update theme preference", description = "Patches user theme preference (LIGHT, DARK, SYSTEM).")
    @ApiResponse(responseCode = "200", description = "Theme preference updated")
    @PatchMapping("/theme")
    public ResponseEntity<UserPreferenceResponse> updateTheme(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId,
            @RequestParam("theme") String theme) {
        UserPreferenceResponse updated = userPreferenceService.updateTheme(currentUserId, theme);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Update language preference", description = "Patches user language preference (e.g. en, es, fr).")
    @ApiResponse(responseCode = "200", description = "Language preference updated")
    @PatchMapping("/language")
    public ResponseEntity<UserPreferenceResponse> updateLanguage(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId,
            @RequestParam("language") String language) {
        UserPreferenceResponse updated = userPreferenceService.updateLanguage(currentUserId, language);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Update timezone preference", description = "Patches user timezone preference (e.g. UTC, Asia/Kolkata).")
    @ApiResponse(responseCode = "200", description = "Timezone preference updated")
    @PatchMapping("/timezone")
    public ResponseEntity<UserPreferenceResponse> updateTimezone(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId,
            @RequestParam("timezone") String timezone) {
        UserPreferenceResponse updated = userPreferenceService.updateTimezone(currentUserId, timezone);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Reset user preferences", description = "Resets user preferences back to system defaults.")
    @ApiResponse(responseCode = "200", description = "Preferences reset to defaults")
    @PostMapping("/reset")
    public ResponseEntity<UserPreferenceResponse> resetPreferences(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId) {
        UserPreferenceResponse reset = userPreferenceService.resetUserPreferencesToDefault(currentUserId);
        return ResponseEntity.ok(reset);
    }
}
