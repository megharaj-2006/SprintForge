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
public class ProjectArchivedEvent extends BaseDomainEvent {
    private String projectName;
    private String reason;

    public static ProjectArchivedEvent create(String projectId, String projectName, String reason, String workspaceId, String triggeredBy) {
        return ProjectArchivedEvent.builder()
                .eventType("ProjectArchivedEvent")
                .aggregateType("PROJECT")
                .aggregateId(projectId)
                .category(EventCategory.PROJECT)
                .projectName(projectName)
                .reason(reason)
                .workspaceId(workspaceId)
                .projectId(projectId)
                .triggeredBy(triggeredBy)
                .build();
    }
}
