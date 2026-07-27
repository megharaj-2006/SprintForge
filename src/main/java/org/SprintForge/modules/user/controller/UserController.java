package org.SprintForge.modules.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.user.dto.*;
import org.SprintForge.modules.user.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Profile Controller", description = "Core APIs for managing user profiles and searching users")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get current authenticated user profile", description = "Returns full profile payload including preferences, stats, and roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile"),
            @ApiResponse(responseCode = "404", description = "User profile not found"),
            @ApiResponse(responseCode = "403", description = "Account deactivated or deleted")
    })
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUserProfile(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId) {
        UserProfileResponse profile = userService.getCurrentUserProfile(currentUserId);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Get public user profile by ID", description = "Returns non-sensitive public user information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved public profile"),
            @ApiResponse(responseCode = "404", description = "Public profile not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PublicUserProfileResponse> getPublicUserProfile(@PathVariable("id") Long id) {
        PublicUserProfileResponse publicProfile = userService.getPublicUserProfile(id);
        return ResponseEntity.ok(publicProfile);
    }

    @Operation(summary = "Update current user profile", description = "Updates username, fullName, displayName, or bio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid profile fields or validation error"),
            @ApiResponse(responseCode = "409", description = "Username already taken")
    })
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId,
            @Valid @RequestBody UpdateProfileRequest request) {
        UserProfileResponse updatedProfile = userService.updateUserProfile(currentUserId, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @Operation(summary = "Check username availability", description = "Verifies if a username format is valid and not already taken.")
    @ApiResponse(responseCode = "200", description = "Username availability status returned")
    @GetMapping("/check-username")
    public ResponseEntity<UsernameCheckResponse> checkUsernameAvailability(
            @RequestParam("username") String username,
            @RequestHeader(value = "X-User-Id", required = false) Long currentUserId) {
        UsernameCheckResponse response = userService.checkUsernameAvailability(username, currentUserId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search users", description = "Paginated search across users by username, email, or full name.")
    @ApiResponse(responseCode = "200", description = "Paginated user search results returned")
    @GetMapping("/search")
    public ResponseEntity<UserSearchResponse> searchUsers(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "username") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        UserSearchResponse result = userService.searchUsers(query, pageable);
        return ResponseEntity.ok(result);
    }
}