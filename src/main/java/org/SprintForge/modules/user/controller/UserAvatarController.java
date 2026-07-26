package org.SprintForge.modules.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.user.dto.UserProfileResponseDto;
import org.SprintForge.modules.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users/me/avatar")
@RequiredArgsConstructor
@Tag(name = "User Avatar Controller", description = "APIs for uploading and removing profile avatars")
public class UserAvatarController {

    private final UserService userService;

    @Operation(summary = "Upload profile avatar", description = "Uploads a new avatar image (JPEG, PNG, GIF, WEBP up to 5MB).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid avatar file size or format")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileResponseDto> uploadProfileAvatar(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId,
            @RequestParam("file") MultipartFile file) {
        UserProfileResponseDto response = userService.uploadProfileAvatar(currentUserId, file);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove profile avatar", description = "Deletes current avatar image and resets profile avatar.")
    @ApiResponse(responseCode = "200", description = "Avatar removed successfully")
    @DeleteMapping
    public ResponseEntity<UserProfileResponseDto> removeProfileAvatar(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long currentUserId) {
        UserProfileResponseDto response = userService.removeProfileAvatar(currentUserId);
        return ResponseEntity.ok(response);
    }
}
