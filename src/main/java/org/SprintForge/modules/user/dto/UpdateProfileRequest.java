package org.SprintForge.modules.user.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.common.validation.annotation.ValidUsername;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

    @ValidUsername
    private String username;

    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @Size(max = 100, message = "Display name must not exceed 100 characters")
    private String displayName;

    @Size(max = 500, message = "Bio must not exceed 500 characters")
    private String bio;

    @Size(max = 255, message = "Headline must not exceed 255 characters")
    private String headline;

    @Size(max = 255, message = "Location must not exceed 255 characters")
    private String location;

    private String skills;

    private String coverImage;

    private String profilePicture;

    private String githubUrl;

    private String linkedinUrl;

    private String websiteUrl;
}
