package org.SprintForge.platform.event.logging;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.dto.ApiResponse;
import org.SprintForge.platform.event.dto.EventLogResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform/events")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class EventLogController {

    private final EventLogService eventLogService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventLogResponse>>> getRecentEvents(@RequestParam(defaultValue = "50") int limit) {
        List<EventLogResponse> logs = eventLogService.getRecentLogs(limit).stream()
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/failed")
    public ResponseEntity<ApiResponse<List<EventLogResponse>>> getFailedEvents() {
        List<EventLogResponse> logs = eventLogService.getFailedLogs().stream()
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventLogResponse>> getEventById(@PathVariable Long id) {
        EventLog eventLog = eventLogService.getLogById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToResponse(eventLog)));
    }

    private EventLogResponse mapToResponse(EventLog log) {
        return EventLogResponse.builder()
                .id(log.getId())
                .eventId(log.getEventId())
                .eventType(log.getEventType())
                .aggregateType(log.getAggregateType())
                .aggregateId(log.getAggregateId())
                .listener(log.getListener())
                .status(log.getStatus())
                .durationMs(log.getDurationMs())
                .occurredAt(log.getOccurredAt())
                .processedAt(log.getProcessedAt())
                .errorTrace(log.getErrorTrace())
                .build();
    }
}
