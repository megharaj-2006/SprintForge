package org.SprintForge.modules.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.user.dto.*;
import org.SprintForge.modules.user.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Account Controller", description = "APIs for account lifecycle, soft delete, restore, deactivation, statistics, and data export")
public class UserAccountController {

    private final UserService userService;

    @Operation(summary = "Soft delete current account", description = "Sets is_deleted = true on user account.")
    @ApiResponse(responseCode = "204", description = "Account soft deleted successfully")
    @DeleteMapping("/me")
    public ResponseEntity<Void> softDeleteAccount(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId) {
        userService.softDeleteAccount(currentUserId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Restore soft deleted account", description = "Resets is_deleted = false on user account.")
    @ApiResponse(responseCode = "200", description = "Account restored successfully")
    @PostMapping("/{id}/restore")
    public ResponseEntity<UserProfileResponseDto> restoreDeletedAccount(@PathVariable("id") Long id) {
        UserProfileResponseDto response = userService.restoreDeletedAccount(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deactivate current account", description = "Sets is_suspended = true to suspend account.")
    @ApiResponse(responseCode = "204", description = "Account deactivated successfully")
    @PostMapping("/me/deactivate")
    public ResponseEntity<Void> deactivateAccount(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId) {
        userService.deactivateAccount(currentUserId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reactivate account by ID", description = "Resets is_suspended = false to reactivate account.")
    @ApiResponse(responseCode = "200", description = "Account reactivated successfully")
    @PostMapping("/{id}/reactivate")
    public ResponseEntity<UserProfileResponseDto> reactivateAccount(@PathVariable("id") Long id) {
        UserProfileResponseDto response = userService.reactivateAccount(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get user statistics", description = "Fetches metrics for user workspaces, projects, tasks, and comments.")
    @ApiResponse(responseCode = "200", description = "User statistics retrieved successfully")
    @GetMapping("/me/statistics")
    public ResponseEntity<UserStatisticsDto> getUserStatistics(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId) {
        UserStatisticsDto statistics = userService.getUserStatistics(currentUserId);
        return ResponseEntity.ok(statistics);
    }

    @Operation(summary = "Get user activity feed", description = "Fetches recent activity logs for user.")
    @ApiResponse(responseCode = "200", description = "User activity feed retrieved successfully")
    @GetMapping("/me/activity")
    public ResponseEntity<List<UserActivityDto>> getUserActivityFeed(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<UserActivityDto> activities = userService.getUserActivityFeed(currentUserId, pageable);
        return ResponseEntity.ok(activities);
    }

    @Operation(summary = "Export user data", description = "Generates a full JSON payload export of user profile, preferences, statistics, and activities.")
    @ApiResponse(responseCode = "200", description = "User data exported successfully")
    @GetMapping("/me/export")
    public ResponseEntity<UserDataExportDto> exportUserData(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId) {
        UserDataExportDto exportData = userService.exportUserData(currentUserId);
        return ResponseEntity.ok(exportData);
    }
}
