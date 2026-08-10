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
public class TaskUpdatedEvent extends BaseDomainEvent {
    private String fieldName;
    private String oldValue;
    private String newValue;

    public static TaskUpdatedEvent create(String taskId, String fieldName, String oldValue, String newValue, String workspaceId, String projectId, String triggeredBy) {
        return TaskUpdatedEvent.builder()
                .eventType("TaskUpdatedEvent")
                .aggregateType("TASK")
                .aggregateId(taskId)
                .category(EventCategory.TASK)
                .fieldName(fieldName)
                .oldValue(oldValue)
                .newValue(newValue)
                .workspaceId(workspaceId)
                .projectId(projectId)
                .triggeredBy(triggeredBy)
                .build();
    }
}
