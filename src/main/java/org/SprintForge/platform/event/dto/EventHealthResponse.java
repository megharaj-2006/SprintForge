package org.SprintForge.platform.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventHealthResponse {
    private String status;
    private int registeredListenersCount;
    private long deadLetterQueueSize;
    private long pendingOutboxEvents;
    private Map<String, String> listenersStatus;
}
