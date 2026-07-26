package org.SprintForge.modules.workspace.analytics.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCreateRequest {

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;

    private Long userId;

    @NotBlank(message = "Dashboard name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    private String description;
    private String layoutConfiguration;
    private Boolean isDefault;
}
