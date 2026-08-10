package org.SprintForge.platform.event.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.SprintForge.platform.event.metadata.EventCategory;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskCreatedEvent extends BaseDomainEvent {
    private String title;
    private String description;
    private String assigneeId;

    public static TaskCreatedEvent create(String taskId, String title, String description, String assigneeId, String workspaceId, String projectId, String triggeredBy) {
        return TaskCreatedEvent.builder()
                .eventType("TaskCreatedEvent")
                .aggregateType("TASK")
                .aggregateId(taskId)
                .category(EventCategory.TASK)
                .title(title)
                .description(description)
                .assigneeId(assigneeId)
                .workspaceId(workspaceId)
                .projectId(projectId)
                .triggeredBy(triggeredBy)
                .build();
    }
}
