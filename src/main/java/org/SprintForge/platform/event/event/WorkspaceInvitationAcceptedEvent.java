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
public class WorkspaceInvitationAcceptedEvent extends BaseDomainEvent {
    private String invitationId;
    private String userEmail;
    private String role;

    public static WorkspaceInvitationAcceptedEvent create(String workspaceId, String invitationId, String userEmail, String role, String triggeredBy) {
        return WorkspaceInvitationAcceptedEvent.builder()
                .eventType("WorkspaceInvitationAcceptedEvent")
                .aggregateType("WORKSPACE")
                .aggregateId(workspaceId)
                .category(EventCategory.WORKSPACE)
                .invitationId(invitationId)
                .userEmail(userEmail)
                .role(role)
                .workspaceId(workspaceId)
                .triggeredBy(triggeredBy)
                .build();
    }
}
