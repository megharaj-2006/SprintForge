package org.SprintForge.platform.event.retry;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.listener.PlatformEventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeadLetterService {

    private final DeadLetterEventRepository deadLetterRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Transactional
    public DeadLetterEvent recordDeadLetter(BaseDomainEvent event, PlatformEventListener listener, Throwable throwable, int attempts) {
        String payload = objectMapper.writeValueAsString(event);
        DeadLetterEvent deadLetter = DeadLetterEvent.builder()
                .originalEventId(event.getEventId())
                .eventType(event.getEventType())
                .aggregateType(event.getAggregateType())
                .aggregateId(event.getAggregateId())
                .listener(listener.getName())
                .reason(throwable.getMessage() != null ? throwable.getMessage() : throwable.toString())
                .attempts(attempts)
                .payload(payload)
                .build();

        log.error("[DeadLetterQueue] Storing DLQ record for event: {} listener: {} after {} attempts",
                event.getEventId(), listener.getName(), attempts);
        return deadLetterRepository.save(deadLetter);
    }

    @Transactional(readOnly = true)
    public List<DeadLetterEvent> getAllDeadLetters() {
        return deadLetterRepository.findAll();
    }

    @Transactional(readOnly = true)
    public DeadLetterEvent getDeadLetterById(Long id) {
        return deadLetterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeadLetterEvent", "id", id));
    }

    @Transactional
    public void discardEvent(Long id) {
        DeadLetterEvent dlq = getDeadLetterById(id);
        log.info("[DeadLetterQueue] Discarding DLQ entry: {}", id);
        deadLetterRepository.delete(dlq);
    }
}
