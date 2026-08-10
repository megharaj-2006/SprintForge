package org.SprintForge.platform.event.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMetadata {
    private String ipAddress;
    private String userAgent;
    private String requestId;
    private String sessionId;
    private String source;
    private String tenantId;
    private String traceId;
}
