package org.SprintForge.modules.workspace.workspace.security.webhook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookDeliveryResponse {
    private Long id;
    private Long webhookId;
    private Long workspaceId;
    private String eventType;
    private String payload;
    private Integer responseStatus;
    private Boolean isSuccess;
    private Integer attemptNumber;
    private Long durationMs;
    private String errorMessage;
    private LocalDateTime createdAt;
}
