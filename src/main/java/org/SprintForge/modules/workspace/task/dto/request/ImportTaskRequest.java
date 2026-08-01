package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportTaskRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private String format; // CSV, EXCEL, JIRA_CSV, CLICKUP_CSV

    private String fileContentBase64;
}
