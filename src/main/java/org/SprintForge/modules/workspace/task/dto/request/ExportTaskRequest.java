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
public class ExportTaskRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private String format; // CSV, EXCEL, JSON, PDF
}
