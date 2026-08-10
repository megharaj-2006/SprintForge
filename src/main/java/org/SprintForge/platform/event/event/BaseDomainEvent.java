package org.SprintForge.platform.event.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.SprintForge.platform.event.metadata.EventCategory;
import org.SprintForge.platform.event.metadata.EventMetadata;
import org.SprintForge.platform.event.metadata.EventPriority;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDomainEvent {

    @lombok.Builder.Default
    private String eventId = UUID.randomUUID().toString();

    private String eventType;
    private String aggregateType;
    private String aggregateId;

    @lombok.Builder.Default
    private Instant occurredAt = Instant.now();

    private String triggeredBy;
    private String workspaceId;
    private String projectId;

    @lombok.Builder.Default
    private String correlationId = UUID.randomUUID().toString();

    private String causationId;

    @lombok.Builder.Default
    private String version = "1.0";

    @lombok.Builder.Default
    private String schemaVersion = "1.0";

    private EventCategory category;

    @lombok.Builder.Default
    private EventPriority priority = EventPriority.NORMAL;

    private EventMetadata metadata;
}
