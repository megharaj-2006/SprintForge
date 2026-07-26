package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskSummaryResponse {

    private Long id;
    private String taskNumber;
    private String title;
    private TaskType type;
    private Long statusId;
    private Long priorityId;
    private Long assigneeId;
    private String assigneeName;
    private Integer storyPoints;
    private LocalDate dueDate;
}
