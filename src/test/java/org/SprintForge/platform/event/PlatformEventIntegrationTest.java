package org.SprintForge.platform.event;

import org.SprintForge.platform.event.dto.EventHealthResponse;
import org.SprintForge.platform.event.dto.EventMetricsResponse;
import org.SprintForge.platform.event.event.TaskCreatedEvent;
import org.SprintForge.platform.event.event.TaskUpdatedEvent;
import org.SprintForge.platform.event.logging.EventLog;
import org.SprintForge.platform.event.logging.EventLogService;
import org.SprintForge.platform.event.metadata.EventStatus;
import org.SprintForge.platform.event.monitoring.EventMetricsService;
import org.SprintForge.platform.event.monitoring.EventMonitoringService;
import org.SprintForge.platform.event.outbox.OutboxEvent;
import org.SprintForge.platform.event.outbox.OutboxEventRepository;
import org.SprintForge.platform.event.outbox.OutboxProcessor;
import org.SprintForge.platform.event.publisher.PlatformEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class PlatformEventIntegrationTest {

    @Autowired
    private PlatformEventPublisher eventPublisher;

    @Autowired
    private EventLogService eventLogService;

    @Autowired
    private EventMetricsService eventMetricsService;

    @Autowired
    private EventMonitoringService eventMonitoringService;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Autowired
    private OutboxProcessor outboxProcessor;

    @BeforeEach
    void setUp() {
        outboxEventRepository.deleteAll();
    }

    @Test
    @DisplayName("Should publish synchronous event and invoke listeners with logs")
    void testSynchronousEventPublishing() {
        TaskCreatedEvent event = TaskCreatedEvent.create(
                "task-101",
                "Fix login bug",
                "Users cannot login",
                "user-1",
                "ws-1",
                "proj-1",
                "user-admin"
        );

        eventPublisher.publish(event);

        List<EventLog> logs = eventLogService.getLogsByAggregate("TASK", "task-101");
        assertThat(logs).isNotEmpty();
        assertThat(logs.stream().anyMatch(l -> l.getListener().equals("NotificationListener"))).isTrue();
        assertThat(logs.stream().anyMatch(l -> l.getListener().equals("ActivityListener"))).isTrue();
    }

    @Test
    @DisplayName("Should save event to transactional outbox and process it via OutboxProcessor")
    void testTransactionalOutboxPublishing() {
        TaskUpdatedEvent event = TaskUpdatedEvent.create(
                "task-102",
                "status",
                "TODO",
                "IN_PROGRESS",
                "ws-1",
                "proj-1",
                "user-admin"
        );

        eventPublisher.publishTransactional(event);

        List<OutboxEvent> pending = outboxEventRepository.findByStatusOrderByCreatedAtAsc(EventStatus.PENDING, Pageable.unpaged());
        assertThat(pending).hasSize(1);
        assertThat(pending.get(0).getAggregateId()).isEqualTo("task-102");

        outboxProcessor.processOutbox();

        OutboxEvent processed = outboxEventRepository.findById(pending.get(0).getId()).orElseThrow();
        assertThat(processed.getStatus()).isEqualTo(EventStatus.COMPLETED);
    }

    @Test
    @DisplayName("Should retrieve monitoring metrics and health status")
    void testMetricsAndHealth() {
        EventMetricsResponse metrics = eventMetricsService.getMetrics();
        assertThat(metrics).isNotNull();
        assertThat(metrics.getListenerHealth()).containsEntry("NotificationListener", "UP");

        EventHealthResponse health = eventMonitoringService.getHealthStatus();
        assertThat(health).isNotNull();
        assertThat(health.getStatus()).isEqualTo("UP");
        assertThat(health.getRegisteredListenersCount()).isGreaterThan(0);
    }
}
