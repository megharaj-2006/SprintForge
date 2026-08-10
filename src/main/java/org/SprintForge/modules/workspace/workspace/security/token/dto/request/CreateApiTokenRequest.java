package org.SprintForge.modules.workspace.workspace.security.token.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApiTokenRequest {
    @NotBlank
    private String name;
    private List<String> permissions;
    private LocalDateTime expiresAt;
}
