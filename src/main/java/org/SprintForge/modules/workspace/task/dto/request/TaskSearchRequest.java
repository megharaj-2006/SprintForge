package org.SprintForge.modules.workspace.task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskSearchRequest {

    private Long workspaceId;
    private Long projectId;
    private Long sprintId;
    private Long epicId;
    private String query;
    private TaskType type;
    private List<Long> statusIds;
    private List<Long> priorityIds;
    private List<Long> assigneeIds;
    private List<Long> reporterIds;
    private Boolean isArchived;

    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 20;
    @Builder.Default
    private String sortBy = "createdAt";
    @Builder.Default
    private String sortDirection = "DESC";
}
