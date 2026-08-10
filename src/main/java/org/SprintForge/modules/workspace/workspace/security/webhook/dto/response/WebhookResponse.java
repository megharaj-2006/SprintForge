package org.SprintForge.modules.workspace.workspace.security.webhook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookResponse {
    private Long id;
    private Long workspaceId;
    private String name;
    private String url;
    private List<String> events;
    private Boolean isActive;
    private Integer failureCount;
    private LocalDateTime lastDeliveryAt;
    private String lastDeliveryStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
