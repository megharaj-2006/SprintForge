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
public class ReleasePublishedEvent extends BaseDomainEvent {
    private String releaseVersion;
    private String releaseNotes;

    public static ReleasePublishedEvent create(String releaseId, String releaseVersion, String releaseNotes, String workspaceId, String projectId, String triggeredBy) {
        return ReleasePublishedEvent.builder()
                .eventType("ReleasePublishedEvent")
                .aggregateType("SYSTEM")
                .aggregateId(releaseId)
                .category(EventCategory.SYSTEM)
                .releaseVersion(releaseVersion)
                .releaseNotes(releaseNotes)
                .workspaceId(workspaceId)
                .projectId(projectId)
                .triggeredBy(triggeredBy)
                .build();
    }
}
