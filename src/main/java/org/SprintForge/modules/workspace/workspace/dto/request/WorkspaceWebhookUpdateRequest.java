package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceWebhookUpdateRequest {

    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    private String url;

    private String secret;

    private String events;

    private Boolean isActive;
}
