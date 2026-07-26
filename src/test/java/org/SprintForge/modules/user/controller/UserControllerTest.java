package org.SprintForge.modules.user.controller;

import org.SprintForge.common.exception.GlobalExceptionHandler;
import org.SprintForge.common.util.JsonUtil;
import org.SprintForge.modules.user.dto.UserProfileResponseDto;
import org.SprintForge.modules.user.dto.UsernameCheckResponseDto;
import org.SprintForge.modules.user.dto.UpdateProfileRequestDto;
import org.SprintForge.modules.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getCurrentUserProfile_ShouldReturn200() throws Exception {
        UserProfileResponseDto dto = UserProfileResponseDto.builder()
                .id(1L)
                .username("john_doe")
                .email("john@example.com")
                .displayName("John Doe")
                .avatar("/uploads/avatars/user_1.png")
                .emailVerified(true)
                .build();

        when(userService.getCurrentUserProfile(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/users/me").header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.emailVerified").value(true));
    }

    @Test
    void checkUsernameAvailability_ShouldReturn200() throws Exception {
        UsernameCheckResponseDto response = UsernameCheckResponseDto.builder()
                .username("new_user")
                .isAvailable(true)
                .message("Username is available")
                .build();

        when(userService.checkUsernameAvailability(eq("new_user"), any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/check-username").param("username", "new_user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAvailable").value(true));
    }

    @Test
    void updateUserProfile_WithValidData_ShouldReturn200() throws Exception {
        UpdateProfileRequestDto request = UpdateProfileRequestDto.builder()
                .username("valid_username")
                .fullName("John Doe")
                .displayName("John D.")
                .bio("Software Engineer")
                .build();

        UserProfileResponseDto response = UserProfileResponseDto.builder()
                .id(1L)
                .username("valid_username")
                .displayName("John D.")
                .build();

        when(userService.updateUserProfile(eq(1L), any(UpdateProfileRequestDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/users/me")
                        .header("X-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("valid_username"))
                .andExpect(jsonPath("$.displayName").value("John D."));
    }

    @Test
    void updateUserProfile_WithInvalidUsername_ShouldReturn400() throws Exception {
        UpdateProfileRequestDto request = UpdateProfileRequestDto.builder()
                .username("admin") // Reserved word
                .fullName("John Doe")
                .build();

        mockMvc.perform(put("/api/v1/users/me")
                        .header("X-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors.username").exists());
    }
}
