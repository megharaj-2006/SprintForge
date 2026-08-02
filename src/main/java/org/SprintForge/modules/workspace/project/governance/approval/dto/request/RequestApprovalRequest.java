package org.SprintForge.modules.workspace.project.governance.approval.dto.request;

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
public class RequestApprovalRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotBlank(message = "Entity type is required")
    private String entityType;

    @NotNull(message = "Entity ID is required")
    private Long entityId;

    @NotBlank(message = "Approval title is required")
    @Size(min = 2, max = 150, message = "Approval title must be between 2 and 150 characters")
    private String title;

    private String comments;
}
